package com.test.securityjwt.demo.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author: zhuqz
 * @date: 2021/3/4 09:33
 * @description:
 */
public class Test {
    //密码加密
    public static String pwdEncode(String pwd){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        pwd = bCryptPasswordEncoder.encode(pwd);
        System.out.println(pwd);
        return pwd;
    }

    public static void main(String[] args) {
        pwdEncode("123456");
    }
}