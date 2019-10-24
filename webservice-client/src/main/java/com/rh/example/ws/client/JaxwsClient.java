package com.rh.example.ws.client;

import com.rh.example.ws.client.stub.MobileAddressImpl;
import com.rh.example.ws.client.stub.MobileAddressImplService;

/**
 * Created by ruhui on 2019/8/25.
 * content:
 */
public class JaxwsClient {
    public static void main(String[] args) {
        // 1. 实例化服务类
        MobileAddressImplService mas = new MobileAddressImplService();

        // 2. 通过服务类得到本地代理类
        MobileAddressImpl ma = mas.getMobileAddressImplPort();

        // 3. 调用本地代理对象的方法，得到数据
        System.out.println(ma.getAddressByMobile("13822225555"));
    }
}
