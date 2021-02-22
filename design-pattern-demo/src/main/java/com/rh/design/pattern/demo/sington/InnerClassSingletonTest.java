package com.rh.design.pattern.demo.sington;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 通过反射破坏 静态内部类单例模式
 *
 * 更多精彩请阅读原文： https://mp.weixin.qq.com/s/H51WP8bFgAdjtCTwSndoxQ
 */
public class InnerClassSingletonTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = InnerClassSingleton.class;
        Constructor c = clazz.getDeclaredConstructor(null);
        c.setAccessible(true);
        Object o1 = c.newInstance();

        Object o2 = InnerClassSingleton.getInstance();

        System.out.println(o1.hashCode());
        System.out.println(o2.hashCode());

    }
}
