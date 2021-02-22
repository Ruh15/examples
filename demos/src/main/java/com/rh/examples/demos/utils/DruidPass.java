package com.rh.examples.demos.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.fastjson.JSON;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author hui
 * @desc druid 对密码加解密
 * @date 2020/5/12
 */
public class DruidPass {
    public final static String PUBLICKEY = "publicKey";

    public static void main(String[] args) throws Exception {
        String password = "password";
        String res = decrypt(PUBLICKEY, password);
        System.out.println(res);
        System.out.println(JSON.toJSONString(genKeyPair()));
    }

    /**
     * 根据 publicKey 解密 密码
     * @param publicKey
     * @param password
     * @return
     * @throws Exception
     */
    public static String decrypt(String publicKey, String password) throws Exception {
        return ConfigTools.decrypt(publicKey, password);
    }

    /**
     * 生成秘钥
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public static String[] genKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
        return ConfigTools.genKeyPair(512);
    }
}
