package com.huahua.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration//配置类的注解
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigurerAdapter 拦截所有请求 按照类中的方法进行处理

    //authorizeRequests     所有Security全注解配置实现的开始，表示开始说明需要的权限
    //antMatchers   拦截全路径   permitAll   任何权限都可以访问，直接放行
    //anyRequest    所有的请求   authenticated 认证后才能访问
    //.and() .csrf() .disable() 固定写法，标识使csrf拦截失效
    //csrf：网站攻击
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

}
