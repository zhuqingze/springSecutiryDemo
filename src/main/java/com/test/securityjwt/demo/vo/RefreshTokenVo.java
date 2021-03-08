package com.test.securityjwt.demo.vo;

import com.test.securityjwt.demo.vo.base.ResVo;
import lombok.Data;

/**
 * @author: zhuqz
 * @date: 2021/3/8 16:07
 * @description:
 */
@Data
public class RefreshTokenVo extends ResVo {
   private String token;
}