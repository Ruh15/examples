package com.example.dateformat;

import java.text.ParseException;
import java.util.Date;

/**
 * @author hui
 * @description
 * @date 2021/2/8
 */
public class MyThread extends Thread {

    private String dateStr;
    private String pattern;
    private Date date;

    public MyThread(String dateStr, String pattern) {
        this.dateStr = dateStr;
        this.pattern = pattern;
    }

    public MyThread(Date date, String pattern) {
        this.date = date;
        this.pattern = pattern;
    }

    @Override
    public void run() {
        try {
            if (date != null) {
                DateUtil.format(date, pattern);
            } else if (dateStr !=null && !"".equals(dateStr)) {
                DateUtil.parse(dateStr, pattern);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
