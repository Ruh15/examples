package com.rh.examples.demos.service.impl;

import com.rh.examples.demos.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * description:
 * author: Ruh
 * time: 2018/11/26.
 */
@Service
public class HelloServiceImpl implements HelloService {
    public String sayHello() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello world";
    }
}
