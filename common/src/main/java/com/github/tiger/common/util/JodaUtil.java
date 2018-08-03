package com.github.tiger.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * 时间工具类
 *
 * 使用Joda实现日期及转换功能
 *
 * @author liuhongming
 */
public class JodaUtil {

    /***
     * yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter yyyyMMddHHmmss =
            new DateTimeFormatterBuilder().appendYear(0, 4).appendLiteral("-")
                    .appendMonthOfYear(2).appendLiteral("-")
                    .appendDayOfMonth(2).appendLiteral(" ")
                    .appendHourOfDay(2).appendLiteral(":")
                    .appendMinuteOfHour(2).appendLiteral(":")
                    .appendSecondOfMinute(2).toFormatter();

    /**
     * yyyy-MM-dd
     */
    private static final DateTimeFormatter yyyyMMdd = new DateTimeFormatterBuilder()
            .appendYear(0, 4).appendLiteral("-").appendMonthOfYear(2).appendLiteral("-")
            .appendDayOfMonth(2).toFormatter();
    /**
     * MM-dd
     */
    private static final DateTimeFormatter mmdd = new DateTimeFormatterBuilder()
            .appendMonthOfYear(2).appendLiteral("-").appendDayOfMonth(2).toFormatter();
    /**
     * HH:mm-HH:mm
     */
    private static final DateTimeFormatter hhmmandhhmm = new DateTimeFormatterBuilder()
            .appendHourOfDay(2).appendLiteral(":").appendMinuteOfHour(2)
            .appendLiteral('-')
            .appendHourOfDay(2).appendLiteral(":").appendMinuteOfHour(2)
            .toFormatter();

    /**
     * 当前日期时间
     *
     * @return
     */
    public static DateTime now() {
        return DateTime.now();
    }
}
