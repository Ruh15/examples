package com.rh.example.swaggerdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 * author: Ruh
 * time: 2018/11/5.
 */
@Api(value = "Test")
@RestController
public class TestController {

    @ApiOperation(value = "根据Id获取用户信息", httpMethod = "GET")
    @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "用户id", defaultValue = "1")
    @GetMapping(value = "/get")
    public String test(Integer id){
        return "test user" + id;
    }

}
