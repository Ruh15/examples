package com.rh.project.retrydemo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Retryable {

    /**
     * 最大重试次数
     * @return
     */
    int maxAttemps() default 0;
}