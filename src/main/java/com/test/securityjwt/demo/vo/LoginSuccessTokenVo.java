package com.test.securityjwt.demo.vo;

import com.test.securityjwt.demo.vo.base.ResVo;
import lombok.Data;

/**
 * @author: zhuqz
 * @date: 2021/3/5 11:08
 * @description:
 */
@Data
public class LoginSuccessTokenVo extends ResVo {
    private String token;
    private String refreshToken;

}