package com.tencent.hummingbird.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: 玛卡巴卡萌♡
 * Date: 2023-04-09
 * Time: 10:24
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication().withUser("st1")
                .password("{bcrypt}$2a$10$EkL16jiJJCSXK10BP1jZAuUpTYKb1Oi7wlJplKW4eT7W4IkvOquQe")
                .authorities("/v1");*/
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/js/*",
                        "/css/*",
                        "/img/**",
                        "/bower_components/**",
                        "/login.html",
                        "/register.html",
                        "/register",
                        "/test.html"
                        ).permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login.html") //设置登录页面路径
                .loginProcessingUrl("/login") //设置处理登录的路径,SpringSecurity默认的
                .failureUrl("/login.html?error") //登录失败访问的页面
                .defaultSuccessUrl("/index.html") //登录成功访问的页面
                .and() //上面的配置完毕,开始另一配置
                .logout() //开始设置登出信息
                .logoutUrl("/logout") //登出路径
                .logoutSuccessUrl("/login.html?logout");//设置登出后显示的页面;
    }
}
