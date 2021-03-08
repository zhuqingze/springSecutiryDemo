package com.test.securityjwt.demo.security.config;


import com.test.securityjwt.demo.security.filter.JwtokenAuthenticationFilter;
import com.test.securityjwt.demo.security.handler.JwtAccessDeniedHandler;
import com.test.securityjwt.demo.security.handler.JwtAuthenticationEntryPoint;
import com.test.securityjwt.demo.security.handler.MyAuthenticationFailureHandler;
import com.test.securityjwt.demo.security.handler.MyAuthenticationSuccessHandler;
import com.test.securityjwt.demo.security.provider.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private JwtokenAuthenticationFilter jwtokenAuthenticationFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //JWT验证
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //json拦截位于用户密码之前
        http.addFilterBefore(jwtokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)//授权错误信息处理
                .exceptionHandling()
                //用户访问资源没有携带正确的token
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                //用户访问没有授权资源
                .accessDeniedHandler(jwtAccessDeniedHandler);

        //用户密码验证
        http.csrf().disable();

        http.httpBasic()
            .and()
        .authorizeRequests()
              .antMatchers("/test/**").hasRole("ADMIN")//test 需要admin角色访问大小写敏感，且数据库存放的需要是ROLE_ADMIN
//            .antMatchers("/role/**").permitAll()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
       /* .formLogin()//form表单登录，不允许json传参
            //登录跳转页面
            .loginPage("/auth/login.html")
            //.usernameParameter("username").passwordParameter("password")  //username和password对应前端表单的name键
             //指定自定义form表单请求的路径
            .loginProcessingUrl("/authentication/form")
            .successHandler(myAuthenticationSuccessHandler)
            .failureHandler(myAuthenticationFailureHandler)
            .permitAll()
            .and()*/
        .logout()
            //默认为/logout，不用改
            .logoutUrl("/logout")  
            .logoutSuccessUrl("/auth/logout.html") //默认跳转到 /login
            .deleteCookies("JSESSIONID");   //默认也是会删除cookie的
     }
}