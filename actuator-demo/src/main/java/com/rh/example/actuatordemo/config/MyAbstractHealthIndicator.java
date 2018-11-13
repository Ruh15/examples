package com.rh.example.actuatordemo.config;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * description: 健康端点（第二种方式）
 * author: Ruh
 * time: 2018/11/13.
 */
@Component("my2")
public class MyAbstractHealthIndicator extends AbstractHealthIndicator {
    private static final String VERSION = "v1.0.1";

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int code = 0;
        if (code != 0) {
            builder.down().withDetail("code", code).withDetail("version", VERSION).build();
        }
        builder.withDetail("code", code).withDetail("version", VERSION).up().build();
    }
}
