package com.rh.project.retrydemo.proxy;

import com.rh.project.retrydemo.interceptor.AnnotationAwareRetryOperationsInterceptor;
import org.springframework.cglib.proxy.Enhancer;

public class RetryProxy {

    public Object newProxyInstance(Object target){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new AnnotationAwareRetryOperationsInterceptor());
        return enhancer.create();
    }
}