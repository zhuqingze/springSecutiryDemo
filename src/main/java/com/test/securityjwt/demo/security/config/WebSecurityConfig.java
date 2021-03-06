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
        //JWT??????
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //json??????????????????????????????
        http.addFilterBefore(jwtokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)//????????????????????????
                .exceptionHandling()
                //???????????????????????????????????????token
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                //??????????????????????????????
                .accessDeniedHandler(jwtAccessDeniedHandler);

        //??????????????????
        http.csrf().disable();

        http.httpBasic()
            .and()
        .authorizeRequests()
              .antMatchers("/test/**").hasRole("ADMIN")//test ??????admin????????????????????????????????????????????????????????????ROLE_ADMIN
//            .antMatchers("/role/**").permitAll()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
       /* .formLogin()//form????????????????????????json??????
            //??????????????????
            .loginPage("/auth/login.html")
            //.usernameParameter("username").passwordParameter("password")  //username???password?????????????????????name???
             //???????????????form?????????????????????
            .loginProcessingUrl("/authentication/form")
            .successHandler(myAuthenticationSuccessHandler)
            .failureHandler(myAuthenticationFailureHandler)
            .permitAll()
            .and()*/
        .logout()
            //?????????/logout????????????
            .logoutUrl("/logout")  
            .logoutSuccessUrl("/auth/logout.html") //??????????????? /login
            .deleteCookies("JSESSIONID");   //?????????????????????cookie???
     }
}