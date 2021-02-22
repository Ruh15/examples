package com.example.dateformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hui
 * @description
 * @date 2021/2/8
 */
public class SimpleDateFormatTest extends Thread {
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String name;
    private String dateStr;

    public SimpleDateFormatTest(String name, String dateStr) {
        this.name = name;
        this.dateStr = dateStr;
    }

    @Override
    public void run() {
        try {
            Date date = sdf.parse(dateStr);
            System.out.println(name + ": date:" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new SimpleDateFormatTest("A", "2017-06-10"));
        executorService.execute(new SimpleDateFormatTest("B", "2016-06-06"));
        executorService.shutdown();
    }
}
