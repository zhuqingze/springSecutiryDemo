package com.test.securityjwt.demo.controller;

import com.test.securityjwt.demo.biz.UserBiz;
import com.test.securityjwt.demo.redis.RedisUtil;
import com.test.securityjwt.demo.res.base.ObjectRestResponse;
import com.test.securityjwt.demo.security.jwt.JwtProperties;
import com.test.securityjwt.demo.security.jwt.JwtTokenUtil;
import com.test.securityjwt.demo.security.param.LoginParam;
import com.test.securityjwt.demo.security.param.RefreshTokenParam;
import com.test.securityjwt.demo.security.param.TokenParam;
import com.test.securityjwt.demo.security.service.MyUserDetailsService;
import com.test.securityjwt.demo.security.util.MyUserDetails;
import com.test.securityjwt.demo.vo.LoginSuccessTokenVo;
import com.test.securityjwt.demo.vo.RefreshTokenVo;
import com.test.securityjwt.demo.vo.base.ResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: zhuqz
 * @date: 2021/3/4 16:01
 * @description:
 */
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserBiz userBiz;


    /**
     * rest登录 不使用form登录
     * @param param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<LoginSuccessTokenVo> login(@RequestBody LoginParam param){
        String userName = param.getUserName();
        String pwd = param.getPwd();
        //根据输入的用户密码，读取数据库中信息
        MyUserDetails user = (MyUserDetails) myUserDetailsService.loadUserByUsername(userName);
        //判断是否有效用户
        if (!user.isEnabled()) {
            throw new DisabledException("USER DISABLE");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("USER LOCKED");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("USER EXPIRED");
        } else if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("USER LOGOUT");
        }
        //验证密码
        if (!passwordEncoder.matches(pwd, user.getPassword())) {
            throw new BadCredentialsException("PASSWORD INVALID!");
        }
        // 生成新token
        Map<String,String> tokens = jwtTokenUtil.generateToken(user);
        String accesstoken = tokens.get(jwtTokenUtil.getAccessTokenKey());
        String refreshtoken = tokens.get(jwtTokenUtil.getRefreshTokenKey());
        String rolestoken = tokens.get(jwtTokenUtil.getRoleTokenKey());
        // 保存到 redis
        redisUtil.set(jwtTokenUtil.getAccessTokenKey(userName),accesstoken);
        redisUtil.expire(jwtTokenUtil.getAccessTokenKey(userName), jwtProperties.getAccessExpiration());
        redisUtil.set(jwtTokenUtil.getRefreshTokenKey(userName),refreshtoken);
        redisUtil.expire(jwtTokenUtil.getRefreshTokenKey(userName),jwtProperties.getRefreshExpiration());
        redisUtil.set(jwtTokenUtil.getRoleTokenKey(userName), rolestoken);
        redisUtil.expire(jwtTokenUtil.getRoleTokenKey(userName), jwtProperties.getRolesExpiration());
        LoginSuccessTokenVo loginSuccessTokenVo = new LoginSuccessTokenVo();
        loginSuccessTokenVo.setCode("200");
        loginSuccessTokenVo.setToken(accesstoken);
        loginSuccessTokenVo.setRefreshToken(refreshtoken);
        return new ObjectRestResponse<>().rel(true).data(loginSuccessTokenVo);
    }
    /**
     * 刷新token
     * @param refreshTokenParam
     * @return
     */
    @RequestMapping(value = "/token/refresh", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<RefreshTokenVo>  refreshToken(@RequestBody RefreshTokenParam refreshTokenParam) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        RefreshTokenVo res = new RefreshTokenVo();
        res.setMsg("操作成功");
        restResponse.data(res);
        if (refreshTokenParam == null || refreshTokenParam.getRefreshToken()==null || "".equals(refreshTokenParam.getRefreshToken())) {
            res.setCode("-1");
            res.setMsg("刷新令牌不能为空");
             return restResponse;
        }
        String refreshToken = refreshTokenParam.getRefreshToken();
        refreshToken = refreshToken.substring(jwtProperties.getTokenHead().length());
        if(jwtTokenUtil.isTokenExpired(refreshToken)){
            res.setCode("1000");
            res.setMsg("刷新令牌过期，请重新登录");
            return restResponse;
        }

        String username = jwtTokenUtil.getUserNameFromToken(refreshToken);

        if (username == null) {
            res.setCode("-1");
            res.setMsg("刷新令牌格式有误");
            return restResponse;
        }
        Object refreshtokenObj = redisUtil.get(jwtTokenUtil.getRefreshTokenKey(username));
        if(refreshtokenObj == null){
            res.setCode("1000");
            res.setMsg("刷新令牌过期，请重新登录");
            return restResponse;
        }
        if(!refreshtokenObj.toString().equals(refreshToken)){
            res.setCode("-1");
            res.setMsg("刷新令牌格式有误");
            return restResponse;
        }

        Object accesstokenObj = redisUtil.get(jwtTokenUtil.getAccessTokenKey(username));
        String accesstoken = accesstokenObj==null?"":(String)accesstokenObj;
        String new_accesstoken = jwtTokenUtil.refreshHeadToken(refreshToken, accesstoken);
        if (new_accesstoken == null) {
            res.setCode("-1");
            res.setMsg("令牌格式有误");
            return restResponse;
        }
        if (new_accesstoken == "") {
            res.setMsg("令牌不要频繁刷新");
            res.setToken(accesstoken);
            return restResponse;
        }
        redisUtil.set(jwtTokenUtil.getAccessTokenKey(username), new_accesstoken);
        res.setToken(new_accesstoken);
        return restResponse;
    }

    /**
     * 清空令牌
     * @param tokenParam
     * @return
     */
    @RequestMapping(value = "/token/clear", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<ResVo>  logout(@RequestBody TokenParam tokenParam) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        ResVo resVo = new ResVo();
        resVo.setCode("200");
        resVo.setMsg("操作成功");
        restResponse.data(resVo);
        if (tokenParam == null || tokenParam.getToken()==null || "".equals(tokenParam.getToken())) {
            resVo.setCode("-1");
            resVo.setMsg("令牌不能为空");
            return restResponse;
        }
        String token = tokenParam.getToken();
        token = token.substring(jwtProperties.getTokenHead().length());
        String username = jwtTokenUtil.getUserNameFromToken(token);
        if (username == null) {
             resVo.setCode("-1");
             resVo.setMsg("令牌格式有误");
             return restResponse;
        }

        Object tokenObj = redisUtil.get(jwtTokenUtil.getAccessTokenKey(username));
        if(tokenObj != null){
            if(!tokenObj.toString().equals(token)){
                resVo.setCode("-1");
                resVo.setMsg("令牌格式有误");
                return restResponse;
            }
        }
        redisUtil.expire(jwtTokenUtil.getAccessTokenKey(username), 1);
        redisUtil.expire(jwtTokenUtil.getRefreshTokenKey(username), 1);
        redisUtil.expire(jwtTokenUtil.getRoleTokenKey(username), 1);
        return restResponse;
    }

}