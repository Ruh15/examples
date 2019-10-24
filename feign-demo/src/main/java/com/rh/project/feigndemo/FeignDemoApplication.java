package com.rh.project.feigndemo;

import com.rh.project.feigndemo.service.FeignTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@RestController
@RequestMapping("/feign")
public class FeignDemoApplication {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FeignTestService feignTestService;

    public static void main(String[] args) {
        SpringApplication.run(FeignDemoApplication.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        long start = System.currentTimeMillis();
        String result = "test";
        try {
            result = feignTestService.get();
        } catch (Exception e) {
            logger.error("", e);
            logger.info("time: {}", (System.currentTimeMillis() - start));
            return "error";
        }
        return result;
    }
}
