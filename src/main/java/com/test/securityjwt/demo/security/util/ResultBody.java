package com.test.securityjwt.demo.security.util;

import lombok.Data;

/**
 * @author: zhuqz
 * @date: 2021/3/3 17:22
 * @description:
 */
@Data
public class ResultBody {

    private String code;
    private String message;

    public ResultBody(String code,String message){
        this.code = code;
        this.message  = message;
    }
}