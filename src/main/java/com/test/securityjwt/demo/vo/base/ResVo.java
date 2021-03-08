package com.test.securityjwt.demo.vo.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResVo implements Serializable {
    private static final long serialVersionUID = -1021289967205770057L;
    /**
     * 编码（参考ConfigContants.java）
     */
    private String code;
    /**
     * 消息
     */
    private String msg;

}