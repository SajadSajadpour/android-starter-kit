package com.food.iotsensor.Bluetooth.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    /**
     * 日期格式yyyy-MM-dd
     */
    public static final String DATE = "yyyy-MM-dd";

    /**
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_SHORT = "HH:mm";

    /**
     * 描述：日期格式化
     *
     * @param date 日期
     * @return 输出格式为 yyyy-MM-dd 的字串
     */
    public static String formatDate(Date date) {
        return formatDate(date, DATE);
    }

    /**
     * 描述：获取16位日期时间，yyyyMMddHHmmss
     *
     * @param date 日期
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DATE_TIME);
    }

    /**
     * 描述：日期格式化
     *
     * @param date    日期
     * @param pattern 格式化类型
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        } catch (Exception ex) {
        }
        return "";

    }

    /**
     * 描述：解析日期字串
     *
     * @param dateStr 日期字串
     * @return 按 yyyy-MM-dd HH:mm:ss 格式解析
     */
    public static Date parseString(String dateStr) {
        return parseString(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseStringDate(String dateStr) {
        return parseString(dateStr, "yyyy-MM-dd");
    }

    /**
     * 描述：解析日期字串
     *
     * @param dateStr 日期字串
     * @param pattern 字串日期格式
     * @return 对应日期类型数据
     */
    public static Date parseString(String dateStr, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            if (dateStr!=null) {
                return dateFormat.parse(dateStr);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            System.err.println(dateStr + "转换成日期失败，可能是不符合格式：" + pattern);
        }
        return null;
    }

    /**
     * 描述：间隔时间
     *
     * @param date1
     * @param date2
     * @return 毫秒数
     */
    public static long getDateMiliDispersion(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            return 0L;
        }

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        return time1 - time2;
    }

    /**
     * 描述：间隔天数
     *
     * @param date1
     * @param date2
     * @return 天数
     */
    public static int getDateDiff(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            return 0;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long diff = time1 - time2;

        Long longValue = new Long(diff / 86400000L);
        return longValue.intValue();
    }

    /**
     * 描述：获取指定日期之前多少天的日期
     *
     * @param date 指定日期
     * @param day  天数
     * @return 日期
     */
    public static Date getDataDiff(Date date, int day) {
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        time -= 86400000L * day;
        return new Date(time);
    }

    /**
     * 描述：当前时间与指定时间的差
     *
     * @param str 秒数
     * @return 时间差，单位：秒
     */
    public static int getUnixTime(String str) {
        if ((str == null) || ("".equals(str))) {
            return 0;
        }
        try {
            long utime = Long.parseLong(str) * 1000L;
            Date date1 = new Date(utime);

            Date date = new Date();

            long nowtime = (date.getTime() - date1.getTime()) / 1000L;
            return (int) nowtime;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取时差失败");
        }
        return 0;
    }

    /**
     * 描述：去除日期字串中原“-”和“:”
     *
     * @return
     */
    public static String formatString(String dateTime) {
        if ((dateTime != null) && (dateTime.length() >= 8)) {
            String formatDateTime = dateTime.replaceAll("-", "");
            formatDateTime = formatDateTime.replaceAll(":", "");
            String date = formatDateTime.substring(0, 8);
            return date;
        }

        return "";
    }

    /**
     * 描述：当前时间与指定时间的差
     *
     * @param str yyyy-MM-dd HH:mm:ss 格式的日期
     * @return 时间差，单位：秒
     */
    public static int getTimesper(String str) {
        if ((str == null) || ("".equals(str))) {
            return 0;
        }
        try {
            Date date1 = new Date(Long.parseLong(str));
            Date date = new Date();
            long nowtime = (date.getTime() - date1.getTime()) / 1000L;
            return (int) nowtime;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("日期转换出错");
        }
        return 0;
    }

    /**
     * 描述：获取16位日期时间，yyyyMMddHHmmss
     *
     * @param dateTime 字串日期
     * @return
     */
    public static String formatDateTime(String dateTime) {
        if ((dateTime != null) && (dateTime.length() >= 8)) {
            String formatDateTime = dateTime.replaceAll("-", "");
            formatDateTime = formatDateTime.replaceAll(":", "");
            String date = formatDateTime.substring(0, 8);
            String time = formatDateTime.substring(8).trim();
            for (int i = time.length(); i < 6; ++i) {
                time = time + "0";
            }
            return date + time;
        }

        return "";
    }

    /**
     * 加年
     */
    public static Date yearAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * 加年
     */
    public static Date monthAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * 加年
     */
    public static Date dayAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }
}