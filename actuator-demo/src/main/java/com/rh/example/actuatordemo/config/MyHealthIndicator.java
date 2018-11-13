package com.rh.example.actuatordemo.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 健康端点（第一种方式）
 * 监控磁盘使用空间
 */
@Component("my1")
public class MyHealthIndicator implements HealthIndicator {
    private  static final String VERSION = "v1.0.0";
    @Override
    public Health health() {
        int code = 0;
        if(code != 0){
            Health.down().withDetail("code",code).withDetail("version",VERSION).build();
        }
        return Health.up().withDetail("code",code).withDetail("version",VERSION).up().build();
    }
}