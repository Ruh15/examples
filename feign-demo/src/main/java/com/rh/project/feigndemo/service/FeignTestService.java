package com.rh.project.feigndemo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component // 需要增加@Component注解，因为在Spring中同时存在两个 FeignTestService 的实例，需要增加 @Component 以进行区分。
@FeignClient(name = "service", url = "http://localhost:8080", fallback = FeignTestFallback.class)
public interface FeignTestService {

    @RequestMapping("/test")
    String get();
}