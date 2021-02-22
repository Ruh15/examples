//package com.example.alipay.demo.test.spdb.api;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.Security;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.bouncycastle.crypto.digests.SM3Digest;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
//
///**
// * 浦发银行API开发平台接入实例代码(普通验签/国密版) for JAVA
// * 要求 jdk版本 1.8 以上
// */
//public class SPDBSMSignature {
//
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    // 算法名称
//    public static final String ALGORITHM_NAME = "sm4";
//
//    // P5填充
//    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
//
//    /**
//     * 密钥加密
//     *
//     * @param algorithm 算法名称
//     * @param content 密钥
//     * @param charset 编码格式
//     * @return
//     */
//    public static String keyDigest(String algorithm, String content, String charset) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance(algorithm);
//            digest.update(content.getBytes(charset));
//            byte[] digestBytes = digest.digest();
//            return DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 请求报文体加密
//     *
//     * @param algorithm 算法名称
//     * @param content 请求报文体
//     * @param charset 编码格式
//     * @return
//     */
//    public static String dataDigest(String algorithm, byte[] content, String charset) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance(algorithm);
//            digest.update(content);
//            byte[] digestBytes = digest.digest();
//            return DatatypeConverter.printBase64Binary(digestBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * MD5加密
//     *
//     * @param hash
//     * @return
//     */
//    public static String md5Digest(String hash) {
//        String md5Str = DigestUtils.md5Hex(hash);
//        return md5Str;
//    }
//
//    /**
//     *
//     * 说明：sm3加密处理
//     *
//     * @param data
//     * @return 2019年11月26日
//     *
//     */
//    public static String sm3(String data) {
//        String charset = "UTF-8";
//        String sm3Data = "";
//        try {
//            byte[] dataBytes = data.getBytes(charset);
//            byte[] hashBytes = hash(dataBytes);
//            sm3Data = ByteUtils.toHexString(hashBytes);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return sm3Data;
//    }
//
//    /**
//     *
//     * 返回长度为32位的byte数组 生成对应的hash值
//     *
//     * @param dataBytes
//     * @return 2019年10月28日
//     *
//     */
//    public static byte[] hash(byte[] dataBytes) {
//        SM3Digest digest = new SM3Digest();
//        digest.update(dataBytes, 0, dataBytes.length);
//        byte[] hash = new byte[digest.getDigestSize()];
//        digest.doFinal(hash, 0);
//        return hash;
//    }
//
//    /**
//     * P5填充加密
//     *
//     * @param key 密钥
//     * @param data 请求报文体
//     * @return
//     */
//    public static byte[] encrypt(byte[] key, byte[] data) {
//        try {
//            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING,
//                    BouncyCastleProvider.PROVIDER_NAME);
//            SecretKeySpec sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
//            cipher.init(Cipher.ENCRYPT_MODE, sm4Key);
//            return cipher.doFinal(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * P5填充解密
//     *
//     * @param key 密钥
//     * @param signature 签名
//     * @return
//     */
//    public static byte[] decrypt(byte[] key, byte[] signature) {
//        try {
//            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING,
//                    BouncyCastleProvider.PROVIDER_NAME);
//            SecretKeySpec sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
//            cipher.init(Cipher.DECRYPT_MODE, sm4Key);
//            return cipher.doFinal(signature);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//   /**
//     * 签名
//     *
//     * @param key 密钥
//     * @param data 请求报文体
//     * @return
//     */
//    public static String sign(String key, byte[] data) {
//        try {
//            String charset = "UTF-8";
//
//            String shaKey = keyDigest("SHA-256", key, charset);
//            String sm3Key = sm3(shaKey);
//            String sm4Key = md5Digest(sm3Key);
//            String sm4Data = sm3(dataDigest("SHA-1", data, charset));
//
//            byte[] keyBytes = ByteUtils.fromHexString(sm4Key);
//            byte[] dataBytes = sm4Data.getBytes(charset);
//            byte[] encryptBytes = encrypt(keyBytes, dataBytes);
//            String hexSignature = ByteUtils.toHexString(encryptBytes).toUpperCase();
//            byte[] signBytes = hexSignature.getBytes(charset);
//            return DatatypeConverter.printBase64Binary(signBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 验签
//     *
//     * @param sm4Key 密钥
//     * @param signature 签名
//     * @param data 请求报文体
//     * @return
//     */
//    public static boolean validateSign(String key, String signature, byte[] data) {
//        String charset = "UTF-8";
//        String shaKey = keyDigest("SHA-256", key, charset);
//        String sm3Key = sm3(shaKey);
//        String sm4Key = md5Digest(sm3Key);
//        byte[] keyBytes = ByteUtils.fromHexString(sm4Key);
//        String sm4Data = sm3(dataDigest("SHA-1", data, charset));
//        byte[] signBytes = DatatypeConverter.parseBase64Binary(signature);
//        String hexSignature = new String(signBytes).toLowerCase();
//        byte[] cipherBytes = ByteUtils.fromHexString(hexSignature);
//        byte[] decrypt = decrypt(keyBytes, cipherBytes);
//        String cipherData = new String(decrypt);
//        return sm4Data.equals(cipherData);
//    }
//
//
//    /**
//     * sm4签名验签测试
//     *
//     * @param args
//     * @throws UnsupportedEncodingException
//     */
//    public static void main(String[] args) throws UnsupportedEncodingException {
//    	String charset = "UTF-8";
//		//加解密样例仅供参考，秘钥有过期时间，建议在生产环境不要将秘钥硬编码在代码中
//        String key = "OWZwMp00YjPlLWJxMTAt************Mjc0MC40OTc0MDMwMTg2MjM3NzY4MC45";
//        System.out.println("X-SPDB-ClientID-Secret:" + key);
//        String data = "{\"name\":\"zhangshan\"}";
//        boolean forbidden = false;// 防重放参数
//        String newBodyData = data;
//        if (forbidden) {
//            long timestamp = System.currentTimeMillis();// X-SPDB-Timestamp
//            String nonce = "TRAN10145581";// X-SPDB-Nonce
//            newBodyData = data + timestamp + nonce;
//        }
//
//        byte[] dataBytes = newBodyData.getBytes(charset);
//        System.out.println("原报文体:" + new String(dataBytes));
//
//        String sign = sign(key, dataBytes);
//        System.out.println("X-SPDB-SIGNATURE:" + sign);
//
//        boolean validateSign = validateSign(key, sign, dataBytes);
//        System.out.println("验签结果:" + validateSign);
//    }
//}
