package com.rh.project.feigndemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/3/13
 **/
@Component
public class FeignTestFallback implements FeignTestService{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String get() {
        logger.info("fall back end");
        return "fall back end";
    }
}
