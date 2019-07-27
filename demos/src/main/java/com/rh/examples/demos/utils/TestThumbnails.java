package com.rh.examples.demos.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.net.URL;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/7/26
 **/
public class TestThumbnails {
    public static void main(String[] args) {
        String str = "https://xxx/xxx.png";
        try {
            URL url = new URL(str);
            Thumbnails.of(url)
                    .scale(0.5f)// 指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽。
                    .outputQuality(1f) // 图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差
                    .toFile("E://xxx//xxx.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
