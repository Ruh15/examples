package com.rh.project.retrydemo.service.impl;

import com.rh.project.retrydemo.annotation.Retryable;
import com.rh.project.retrydemo.service.UserRetryService;

public class UserRetryServiceImpl implements UserRetryService {
    private int count = 0;

    @Retryable(maxAttemps = 5)
    public void testRetry() {
        System.out.println("这是第 ：" + count + "次执行方法");
        count ++;
        throw new RuntimeException();
    }
}
