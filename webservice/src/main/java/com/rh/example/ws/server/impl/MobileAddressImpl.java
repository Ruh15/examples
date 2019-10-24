package com.rh.example.ws.server.impl;

import com.rh.example.ws.server.MobileAddress;

import javax.jws.WebService;

/**
 * Created by ruhui on 2019/8/25.
 * content:
 */
@WebService
public class MobileAddressImpl implements MobileAddress {
    public String getAddressByMobile(String mobile) {
        // 一系列逻辑处理之后返回结果。这里只是 测试
        return mobile + "归属地是：上海";
    }
}
