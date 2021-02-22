package com.example.alipay.demo.test.spdb;

import com.example.alipay.demo.util.JaxbXmlUtil;

import java.util.Base64;

/**
 * @author hui
 * @description 组装参数工具类
 * @date 2021/2/8
 */
public class SpdbDataUtil {

    public static String getXmlData(Object xmlData) {
        String str = "";
        try {
            str = JaxbXmlUtil.convertToXmlNoHead(xmlData, JaxbXmlUtil.DEFAULT_ENCODING);
            System.err.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
