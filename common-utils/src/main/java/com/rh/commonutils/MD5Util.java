package com.rh.commonutils;

import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getDigest(InputStream is, MessageDigest md, int byteArraySize) throws IOException {

        md.reset();
        byte[] bytes = new byte[byteArraySize];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            md.update(bytes, 0, numBytes);
        }
        byte[] digest = md.digest();
        String result = new String(Hex.encodeHex(digest));
        return result;
    }

    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String path = "E:\\working\\xxx.dat";
            System.out.println(MD5Util.getDigest(new FileInputStream(path), md, 2048));
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}  