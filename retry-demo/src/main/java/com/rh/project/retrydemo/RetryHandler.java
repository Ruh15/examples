package com.rh.project.retrydemo;

import com.rh.project.retrydemo.proxy.RetryProxy;
import com.rh.project.retrydemo.service.RetryService;
import com.rh.project.retrydemo.service.UserRetryService;
import com.rh.project.retrydemo.service.impl.UserRetryServiceImpl;

public class RetryHandler {

    public static void main(String[] args) {

        UserRetryServiceImpl retryServiceImpl = new UserRetryServiceImpl();
        RetryProxy retryProxy = new RetryProxy();
        //创建代理类
        UserRetryService retryService =(UserRetryService)retryProxy.newProxyInstance(retryServiceImpl);
        retryService.testRetry();
        retryService.testRetry();
    }
}