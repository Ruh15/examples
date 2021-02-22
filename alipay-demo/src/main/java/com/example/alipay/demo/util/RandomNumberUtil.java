package com.example.alipay.demo.util;

import java.util.Random;

/**
 * 创建验证码工具类
 */
public class RandomNumberUtil {

    public static String newRandomNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9000) + 1000);
    }

    public static String getRandomNumber2() {
        Random random = new Random();
        return String.valueOf(random.nextInt(90) + 10);
    }

}
