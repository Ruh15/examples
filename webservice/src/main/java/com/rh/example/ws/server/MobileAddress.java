package com.rh.example.ws.server;

/**
 * Created by ruhui on 2019/8/25.
 * content:  sei 的接口类，定义业务方法
 */
public interface MobileAddress {

    /**
     * 根据手机号查询归属地
     * @param mobile
     * @return
     */
    String getAddressByMobile(String mobile);
}
