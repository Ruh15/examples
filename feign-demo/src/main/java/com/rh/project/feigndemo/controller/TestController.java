package com.rh.project.feigndemo.controller;

import com.rh.project.feigndemo.service.FeignTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/3/13
 **/
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/test")
    public String test() throws InterruptedException {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(2);
        logger.info("time: {}", (System.currentTimeMillis() - start));
        return "test";
    }

}
