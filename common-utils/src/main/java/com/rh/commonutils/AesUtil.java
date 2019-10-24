package com.rh.commonutils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 加密工具类
 *
 * @date 2018/11/12
 */
public class AesUtil {
    /**
     * 算法
     */
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    /**
     * 加密解密方式
     */
    private static final String AES = "AES";

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     */
    private static byte[] base64Decode(String base64Code) {
        return StringUtils.isEmpty(base64Code) ? null : Base64.getDecoder().decode(base64Code);
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     */
    private static byte[] aesEncryptToBytes(String content, String encryptKey) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(StandardCharsets.UTF_8), AES));
            return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
        }
    }


    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     */
    public static String aesEncrypt(String content, String encryptKey) {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     */
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(StandardCharsets.UTF_8), AES));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes, StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new RuntimeException("解密失败");
        }
    }


    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static void main(String[] args) {
        String s =aesDecrypt("rJOdr/cb1+yhW2wCgbpM88R6TFeO5aAP4EWj571UpHTlf/2OvY51hBux2dtkC+W24rh3pSpPLcGEYEb4blE7+ZkMwlJdrGobPeezkCF5HUEiYw9YWe8/Px9ZixVqFuVfiR0gXwLZ58WP0UT4dgAir+028w5YmIht+aJBWvbksUofMDcx70JcukH6RjuL8eQDKaFm99S3CPj1ZgnMPSSE2rpjqxPn8O9i60HtpoEPpNE=","6WBn4281Mo23YKRb");
        System.out.println(s);
    }

}