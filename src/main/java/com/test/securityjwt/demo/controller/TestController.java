package com.test.securityjwt.demo.controller;

import com.test.securityjwt.demo.biz.UserBiz;
import com.test.securityjwt.demo.controller.base.BaseController;
import com.test.securityjwt.demo.entity.User;
import com.test.securityjwt.demo.res.base.ObjectRestResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhuqz
 * @date: 2021/3/4 14:14
 * @description:
 */
@RestController
@RequestMapping("test")
public class TestController  extends BaseController<UserBiz, User> {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<User> user(@RequestParam("name") String name) {
        User user = baseBiz.getUserByName(name);
        return new ObjectRestResponse<>().data(user).rel(true);
    }
}