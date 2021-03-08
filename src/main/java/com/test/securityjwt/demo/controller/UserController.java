package com.test.securityjwt.demo.controller;

import com.test.securityjwt.demo.biz.UserBiz;
import com.test.securityjwt.demo.controller.base.BaseController;
import com.test.securityjwt.demo.entity.User;
import com.test.securityjwt.demo.res.base.ObjectRestResponse;
import com.test.securityjwt.demo.util.ConfigConstants;
import com.test.securityjwt.demo.vo.base.ResVo;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhuqz
 * @date: 2021/3/3 16:31
 * @description:
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserBiz, User> {

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<ResVo> user(@RequestBody User user) {
        baseBiz.insertSelective(user);
        ResVo res = new ResVo();
        res.setCode(ConfigConstants.RES_SUCCESS);
        res.setMsg(user.getId());
        return new ObjectRestResponse<>().data(res).rel(true);
    }
}