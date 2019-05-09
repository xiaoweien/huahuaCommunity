package com.huahua.manager;

import huahua.common.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
//@EnableEurekaClient：这个注解可以省略 只需要配置依赖和yml就可以了
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
