package com.rh.examples.demos.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类的注解Data、NoArgsConstructor和AllArgsConstructor都是lombok提供的简化我们开发的，
 * 主要功能分别是，为我们的类生成set和get方法，生成无参构造器和生成全参构造器。
 * 使用idea进行开发的可以装一下lombok的支持插件
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMsg<T> {

    private int code;

    private String msg;

    private T data;

}