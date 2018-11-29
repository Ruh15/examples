package com.rh.examples.demos.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rh.examples.demos.model.User;
import com.rh.examples.demos.model.Word;

import java.util.*;

/**
 * description:
 * author: Ruh
 * time: 2018/11/8.
 */
public class SerializerFeatureTest {
    private static Word word;

    /**
     * 初始化数据
     */
    static {
        List<User> list = new ArrayList<>();
        User user1 = new User(1, "用户1", "北京", "11");
        User user2 = new User(2, "用户2", "上海", "22");
        User user3 = new User(3, "用户3", "广州", "33");
        list.add(user3);
        list.add(user2);
        list.add(null);
        list.add(user1);

        Map<String , Object> map = new HashMap<>();
        map.put("mapa", "mapa");
        map.put("mapo", "mapo");
        map.put("mapz", "mapz");
        map.put("user1", user1);
        map.put("user3", user3);
        map.put("user4", null);
        map.put("list", list);
        word = new Word("a", 2, true, "d", "", null, new Date(), map, list);

    }

    public static void main(String[] args) {
        useSingleQuotes();
        writeMapNullValue();
//        useISO8601DateFormat();
//        writeNullListAsEmpty();
//        writeNullStringAsEmpty();
//        sortField();
//        prettyFormat();
//        writeDateUseDateFormat();
//        beanToArray();
//        showJsonBySelf();
    }

    /**
     * 9:自定义
     * 格式化输出
     * 显示值为null的字段
     * 将为null的字段值显示为""
     * DisableCircularReferenceDetect:消除循环引用
     */
    private static void showJsonBySelf() {
        System.out.println(JSON.toJSONString(word));
        System.out.println(JSON.toJSONString(word, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty));
    }

    /**
     * 8:
     * 将对象转为array输出
     */
    private static void beanToArray() {
        word.setMap(null);
        word.setList(null);
        System.out.println(JSON.toJSONString(word));
        System.out.println(JSON.toJSONString(word, SerializerFeature.BeanToArray));
    }

    /**
     * 7:
     * WriteDateUseDateFormat:全局修改日期格式,默认为false。
     */
    private static void writeDateUseDateFormat() {
        word.setMap(null);
        word.setList(null);
        System.out.println(JSON.toJSONString(word));
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        System.out.println(JSON.toJSONString(word, SerializerFeature.WriteDateUseDateFormat));
    }

    /**
     * 6:
     * PrettyFormat
     */
    private static void prettyFormat() {
        word.setMap(null);
        word.setList(null);
        System.out.println(JSON.toJSONString(word));
        System.out.println(JSON.toJSONString(word, SerializerFeature.PrettyFormat));
    }

    /**
     * SortField:按字段名称排序后输出。默认为false
     * 这里使用的是fastjson：为了更好使用sort field martch优化算法提升parser的性能，fastjson序列化的时候，
     * 缺省把SerializerFeature.SortField特性打开了。
     * 反序列化的时候也缺省把SortFeidFastMatch的选项打开了。
     * 这样，如果你用fastjson序列化的文本，输出的结果是按照fieldName排序输出的，parser时也能利用这个顺序进行优化读取。
     * 这种情况下，parser能够获得非常好的性能。
     */
    private static void sortField() {
        System.out.println(JSON.toJSONString(word));
        System.out.println(JSON.toJSONString(word, SerializerFeature.SortField));
    }

    /**
     *  5:
     *  WriteNullStringAsEmpty:字符类型字段如果为null,输出为"",而非null
     *  需要配合WriteMapNullValue使用，现将null输出
     */
    private static void writeNullStringAsEmpty() {
        word.setE(null);
        System.out.println(JSONObject.toJSONString(word));
        System.out.println("设置WriteMapNullValue后：");
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.WriteMapNullValue));
        System.out.println("设置WriteMapNullValue、WriteNullStringAsEmpty后：");
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
    }


    /**
     * 4:
     * WriteNullListAsEmpty:List字段如果为null,输出为[],而非null
     * 需要配合WriteMapNullValue使用，现将null输出
     */
    private static void writeNullListAsEmpty() {
        word.setList(null);
        System.out.println(JSONObject.toJSONString(word));
        System.out.println("设置WriteNullListAsEmpty后：");
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty));
    }

    /**
     * 3:
     * UseISO8601DateFormat:Date使用ISO8601格式输出，默认为false
     */
    private static void useISO8601DateFormat() {
        System.out.println(JSONObject.toJSONString(word));
        System.out.println("设置UseISO8601DateFormat后：");
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.UseISO8601DateFormat));
    }

    /**
     * 2:
     * WriteMapNullValue:是否输出值为null的字段,默认为false
     */
    private static void writeMapNullValue() {
        System.out.println(JSONObject.toJSONString(word));
        System.out.println("设置WriteMapNullValue后：");
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 1:
     * UseSingleQuotes:使用单引号而不是双引号,默认为false
     */
    private static void useSingleQuotes() {
        System.out.println(JSONObject.toJSONString(word));
        System.out.println("设置useSingleQuotes后：");
        System.out.println(JSONObject.toJSONString(word, SerializerFeature.UseSingleQuotes));
    }
}
