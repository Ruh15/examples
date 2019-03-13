package com.rh.project.feigndemo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "service", url = "http://localhost:8080", fallback = FeignTestFallback.class)
public interface FeignTestService {

    @RequestMapping("/test")
    String get();
}