package com.rh.example.ws;

import com.rh.example.ws.server.impl.MobileAddressImpl;

import javax.xml.ws.Endpoint;

/**
 * Created by ruhui on 2019/8/25.
 * content: 发布 webservice 服务
 */
public class JaxwServer {

    public static void main(String[] args) {


        Endpoint.publish("http://127.0.0.1:9999/mobile", new MobileAddressImpl());
        System.out.println("WebService 服务启动成功!");
        // 访问的格式：Webservice地址+?wsdl
    }
}
