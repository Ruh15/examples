package com.rh.commonutils.aoputils;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * AOP advicer to get log
 */
@Component
@Aspect
public class MethodLogAdvice {
    private static Logger log = LoggerFactory.getLogger(MethodLogAdvice.class);

    @Around("execution(* ruh.swagger.service.*.*(..))")
    public Object aroundMethodAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object args[] = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // join arguments.
        log.info("{}.{} : 请求参数:{} ", method.getDeclaringClass().getName(), method.getName(), StringUtils.join(args, " ; "));
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeConsuming = System.currentTimeMillis() - start;
        log.info("调用结束--> 返回值:{} 耗时:{}ms", JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue), timeConsuming);
        return result;
    }
}