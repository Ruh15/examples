package com.rh.examples.demos.service.impl;

import com.rh.examples.demos.service.HelloService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * description:
 * author: Ruh
 * time: 2018/11/26.
 */
@Service
public class HelloServiceImpl implements HelloService {
    public String sayHello() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello world";
    }
}
