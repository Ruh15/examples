package com.rh.commonutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.PascalNameFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class DateUtil {

    /**
     * 以template的格式为例，将当前时间格式化为String类型
     *
     * @param template
     * @return
     */
    public static String formatDateToStr(String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        return sdf.format(new Date());
    }

    /**
     * 以template的格式为例，根据传入Date格式化为String类型
     *
     * @param date
     * @param template
     * @return
     */
    public static String formatDateToStr(Date date, String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        return sdf.format(date);
    }

    /**
     * 以String类型转换为Date
     *
     * @param strDate
     * @return
     */
    public static Date formatStrToDate(String strDate, String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        Date date = null;

        try {
            date = sdf.parse(strDate);
        } catch (ParseException var) {
            var.printStackTrace();
        }

        return date;
    }

    /**
     * 根据起始时间段获取两个Date,用于一段时间的筛选
     *
     * @param strDate
     * @param template
     * @return
     */
    public static Date[] formatStrToTwoDate(String strDate, String template) {
        Date[] dates = new Date[2];
        String[] strDates = strDate.split("\\~");
        dates[0] = DateUtil.formatStrToDate(strDates[0]+" 00:00:00",template);
        dates[1] = DateUtil.formatStrToDate(strDates[1]+" 23:59:59",template);

        return dates;
    }

    /**
     * 格式化到23点59分59秒999毫秒
     * @return
     */
    public static Date formatEndOfDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(MINUTE, 59);
        calendar.set(SECOND, 59);
        calendar.set(MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 格式化到零点
     * @return
     */
    public static Date formatBeginOfDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据参数 格式化日期
     * @param date 日期
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @param milliSecond 毫秒
     * @param dateAdd 需要增加多少天
     * @return
     */
    public static Date formatDateByParams(Date date, int hour, int minute, int second, int milliSecond, int dateAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(HOUR_OF_DAY, hour);
        calendar.set(MINUTE, minute);
        calendar.set(SECOND, second);
        calendar.set(MILLISECOND, milliSecond);
        calendar.add(DATE, dateAdd);
        return calendar.getTime();
    }

}
