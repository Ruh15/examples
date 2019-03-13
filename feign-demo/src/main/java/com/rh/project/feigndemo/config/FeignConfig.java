package com.rh.project.feigndemo.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/3/13
 **/
@Configuration
public class FeignConfig {

//    private static final int connectTimeOutMillis = 12000;//超时时间
//    private static final int readTimeOutMillis = 12000;

//    @Bean
//    public Request.Options options() {
//        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
//    }

    /**
     * 自定义重试次数
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
//        return new Retryer.Default();
        return new Retryer.Default(100, 1000, 4);
    }
}
