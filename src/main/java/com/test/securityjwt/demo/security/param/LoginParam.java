package com.test.securityjwt.demo.security.param;

import lombok.Data;

/**
 * @author: zhuqz
 * @date: 2021/3/8 15:58
 * @description:
 */
@Data
public class LoginParam {
    private String userName;
    private String pwd;
}