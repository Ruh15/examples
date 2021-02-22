package com.example.alipay.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

/**
 * @Author: LiuHao
 * @Descirption:
 * @Date: 2018/7/25_16:42
 */
public class DateUtil {

    public static final String DATETIMEFORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMAT_PATTERN = "yyyy-MM-dd";

    /**
     * 判断约定时间是否在当前时间之前
     * @param time
     * @return
     */
    public static boolean before(long time){
        return time<new Date().getTime();
    }

    /**
     * 获取今天是几号
     *
     * @return
     */
    public static int currentDay() {
        return new Date().getDate();
    }

    /**
     * 获取当月是几月
     *
     * @return
     */
    public static int currentMonth() {
        return new Date().getMonth() + 1;
    }

    /**
     * 获取今夕是何年
     *
     * @return
     */
    public static int currentYear() {
        return new Date().getYear() + 1900;
    }

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
     * 以 DATEFORMAT_PATTERN 的格式为例，根据传入Date格式化为String类型
     *
     * @param date
     * @return
     */
    public static String formatDateToStr(Date date) {
        return formatDateToStr(date, DATEFORMAT_PATTERN);
    }

    /**
     * 以String类型转换为Date
     *
     * @param strDate
     * @return
     */
    public static Date formatStrToDate(String strDate) {
        return formatStrToDate(strDate, DATEFORMAT_PATTERN);
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

    /**
     *
     * @param date
     * @param num
     * @throws ParseException
     */
    public static String addDaysOfDate(Date date, int num, String dateTemplate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateTemplate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);
        Date endDate = ca.getTime();
        String endDateStr = format.format(endDate);
        return endDateStr;
    }

    /**
     * 对日期进行增减
     * @param date
     * @param num
     * @return
     */
    public static Date addDaysOfDate(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);
        return ca.getTime();
    }

    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//        String a = addDaysOfDate(format.parse("2018/12/30"), 16, "yyyy/MM/dd");
//        System.out.println(a);
//        formatStrToTwoDate("2018/12/30", "yyyy/MM/dd");
        Date date=new Date();
        Date date1 = addDaysOfDate(date, -1);
        System.out.println("now :"+date+", date1 "+date1);

    }
}
