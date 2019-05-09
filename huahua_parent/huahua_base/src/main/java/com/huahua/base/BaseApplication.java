package com.huahua.base;

import huahua.common.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * 功能描述：BaseApplication
 * @Author LiHuaMing
 * @Date 11:29 2019/4/11
 * @Param
 * @return
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.huahua.base.dao")
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
