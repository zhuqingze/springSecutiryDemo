package com.test.securityjwt.demo.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.securityjwt.demo.security.util.ResultBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //登录失败信息返回
        ResultBody resultBody = new ResultBody("401", "登录失败:"+exception.getLocalizedMessage());
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        //写出流
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        out.write(mapper.writeValueAsString(resultBody));
        out.flush();
        out.close();
    }
}