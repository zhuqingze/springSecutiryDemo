package com.test.securityjwt.demo.biz;

import com.test.securityjwt.demo.biz.base.BaseBiz;
import com.test.securityjwt.demo.entity.User;
import com.test.securityjwt.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhuqz
 * @date: 2021/3/3 16:14
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BaseBiz<UserMapper, User> {
    @Autowired
    UserMapper userMapper;
    @Override
    protected String getPageName() {
        return null;
    }
    public User getUserByName(String name){
          return userMapper.getUserByName(name);
    }
    public int addUser(User user){
        int success = 1,exist = 0;
        User userExist = userMapper.getUserByName(user.getName());
        if(userExist!=null){
            return exist;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPwd = bCryptPasswordEncoder.encode(user.getPwd());
        user.setPwd(newPwd);
        this.insertSelective(user);
        return success;
    }
}