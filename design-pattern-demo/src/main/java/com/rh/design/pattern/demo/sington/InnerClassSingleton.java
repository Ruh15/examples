package com.rh.design.pattern.demo.sington;

import java.io.Serializable;

/**
 * 内部静态类
 */
public class InnerClassSingleton implements Serializable {

    //无参构造函数
    private InnerClassSingleton(){};

    public static final InnerClassSingleton getInstance(){
        return InnerClassHelper.INSTANCE;
    }

    //内部类
    private static class InnerClassHelper{
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }
}
