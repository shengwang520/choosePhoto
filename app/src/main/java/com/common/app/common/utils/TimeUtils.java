package com.common.app.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理
 * Created by 圣王 on 2015/6/4 0004.
 */
public class TimeUtils {
    public static final String TYPE_MMSS = "mm:ss";

    public static Date parseString2Date18(String dateTime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateTime);
        } catch (ParseException e) {
            return TimeUtils.getOldTimeYear(18);
        }
    }

    public static Date parseString2Date(String dateTime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateTime);
        } catch (ParseException e) {
            return new Date();
        }
    }


    public static String getDate2String(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(time);

    }

    /**
     * 获取过去的时间
     *
     * @param year 过去几年
     */
    public static Date getOldTimeYear(int year) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        c.add(Calendar.YEAR, -year);
        return c.getTime();
    }

    /**
     * 获取 几个月前或之后的时间
     *
     * @param month -之前  +之后
     */
    public static Date getOldTimeMonth(int month) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }
}
