package com.rh.example.actuatordemo.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 定义自己的端点
 * author: Ruh
 * time: 2018/11/13.
 */
@Endpoint(id = "ruh")
public class MyEndPoint {

    @ReadOperation
    public Map<String, String> test() {
        Map<String, String> res = new HashMap();
        res.put("name", "Ruh");
        res.put("age", "26");
        return res;
    }
}
