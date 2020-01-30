package com.zxwl.frame.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by hcc on 2016/10/11 16:30
 * 100332338@qq.com
 * <p>
 * 时间工具类
 */

@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public final static String FORMAT_YEAR = "yyyy";

    public final static String FORMAT_MONTH_DAY = "MM月dd日";

    public final static String FORMAT_DATE = "yyyyMMdd";

    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    public final static String FORMAT_TIME = "HH:mm";

    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm";

    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";

    public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";

    public final static String FORMAT_DATE_TIME_2 = "yyyy.MM.dd HH:mm";

    public final static String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss";

    public final static String FORMAT_DATE_TIME_SECOND_HORIZONTAL = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    private static final int YEAR = 365 * 24 * 60 * 60;// 年

    private static final int MONTH = 30 * 24 * 60 * 60;// 月

    private static final int DAY = 24 * 60 * 60;// 天

    private static final int HOUR = 60 * 60;// 小时

    private static final int MINUTE = 60;// 分钟


    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        // 与现在时间相差秒数
        long timeGap = (currentTime - timestamp) / 1000;
        System.out.println("timeGap: " + timeGap);
        String timeStr;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     */
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }

        return sdf.format(new Date().getTime());
    }

    /**
     * date类型转换为String类型
     *
     * @param data       Date类型的时间
     * @param formatType 格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
//        return sdf.format(data);
    }

    /**
     * long类型转换为String类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType  要转换的string类型的时间格式
     */
    public static String longToString(long currentTime, String formatType) {
        String strTime;
        // long类型转成Date类型
        Date date = longToDate(currentTime, formatType);
        // date类型转成String
        strTime = dateToString(date, formatType);
        return strTime;
    }

    /**
     * string 类型转换为date类型
     *
     * @param strTime    要转换的string类型的时间，
     * @param formatType 要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     *                   HH时mm分ss秒,strTime的时间格式必须要与formatType的时间格式相同
     */
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType  要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     */
    public static Date longToDate(long currentTime, String formatType) {
        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currentTime);
        // 把date类型的时间转换为string
        String sDateTime = dateToString(dateOld, formatType);
        // 把String类型转换为Date类型
        return stringToDate(sDateTime, formatType);
    }

    /**
     * String转换为long型
     *
     * @param strTime    要转换的String类型的时间
     * @param formatType 时间格式
     */
    public static long stringToLong(String strTime, String formatType) {
        // String类型转成date类型
        Date date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            // date类型转成long类型
            return dateToLong(date);
        }
    }


    /**
     * date 类型转换为long类型
     *
     * @param date 要转换的date类型的时间
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * SimpleDateFormat格式化long型时间，比真实时间多了8个小时
     * 所以需要设置
     * SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
     * sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
     *
     * @param currentTime 传入的long
     * @return 返回的时间
     */
    public static String longToString(long currentTime) {
        String strTime;
        // long类型转成Date类型
        Date date = longToDate(currentTime, "HH:mm:ss");
        // date类型转成String
//        strTime = dateToString(date, "HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        strTime = sdf.format(date);
        return strTime;
    }

    /**
     * @param time
     * @return
     */
    public static Long convertTimeToLong(String time) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 读出的音乐时长为 long 类型以毫秒数为单位，例如：将 234736 转化为分钟和秒应为 03:55 （包含四舍五入）
     *
     * @param duration
     * @return
     */
    public static String timeParse(long duration) {
        StringBuilder sb = new StringBuilder();
        long minute = duration / 60000;
        long h = minute / 60;
        minute %= 60;

        if (h < 10) {
            sb.append("0");
        }
        sb.append(h).append(":");

        if (minute < 10) {
            sb.append("0");
        }
        sb.append(minute);
        return sb.toString();
    }
}
