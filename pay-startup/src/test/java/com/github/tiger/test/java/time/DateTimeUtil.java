package com.github.tiger.test.java.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author liuhongming
 * @date 2020/02/26
 */
public class DateTimeUtil {

    private static final String UTC_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private static final String LOCAL_DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String LOCAL_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private static final String LOCAL_TIME_FORMAT_PATTERN = "HH:mm:ss";

    private DateTimeUtil() {
        //no instance
    }

    /**
     * 获取当前 LocalDateTime
     *
     * @return
     */
    public static LocalDateTime localDateTimeNow() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前 LocalDate
     *
     * @return
     */
    public static LocalDate localDateNow() {
        return LocalDate.now();
    }

    /**
     * 获取当前 LocalTime
     *
     * @return
     */
    public static LocalTime localTimeNow() {
        return LocalTime.now();
    }

    /**
     * 获取当前 Instant
     *
     * @return
     */
    public static Instant instantNow() {
        return Instant.now();
    }

    /**
     * 获取当前毫秒数
     *
     * @return
     */
    public static long currentMillis() {
        return instantNow().toEpochMilli();
    }

    /**
     * 将时间毫秒转换成 Instant
     *
     * @param epochMillis
     * @return
     */
    public static Instant toInstant(long epochMillis) {
        return Instant.ofEpochMilli(epochMillis);
    }

    /**
     * 将时间毫秒转换成 Date
     *
     * @param epochMillis
     * @return
     */
    public static Date fromEpochMillis(long epochMillis) {
        return Date.from(toInstant(epochMillis));
    }

    /**
     * 将 LocalDateTime 转换成 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date fromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将 LocalDate 转换成 Date
     *
     * @param localDate
     * @return
     */
    public static Date fromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 使用指定的时区，将 LocalDateTime 转换成 Date
     *
     * @param localDateTime
     * @param zoneOffset
     * @return
     */
    public static Date fromLocalDateTime(LocalDateTime localDateTime,
                                         ZoneOffset zoneOffset) {
        return Date.from(localDateTime.atOffset(zoneOffset).toInstant());
    }

    /**
     * 使用指定的时区，将 LocalDate 转换成 Date
     *
     * @param localDate
     * @param zoneOffset
     * @return
     */
    public static Date fromLocalDate(LocalDate localDate,
                                     ZoneOffset zoneOffset) {
        return Date.from(localDate.atStartOfDay(zoneOffset).toInstant());
    }

    /**
     * 使用指定的日期时间格式格式化 LocalDateTime
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 使用指定的日期时间格式格式化 LocalDate
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String format(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 使用指定的日期时间格式格式化 Date
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 使用默认日期时间格式，将日期时间字符串转换成 LocalDateTime
     *
     * @param datetime
     * @return
     */
    public static LocalDateTime parseToLocalDateTime(String datetime) {
        return parseToLocalDateTime(datetime, LOCAL_DATETIME_FORMAT_PATTERN);
    }

    /**
     * 使用指定日期时间格式，将日期时间字符串转换成 LocalDateTime
     *
     * @param datetime
     * @param pattern
     * @return
     */
    public static LocalDateTime parseToLocalDateTime(String datetime, String pattern) {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 使用默认日期时间格式，将日期时间字符串转换成 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate parseToLocalDate(String date) {
        return parseToLocalDate(date, LOCAL_DATE_FORMAT_PATTERN);
    }

    /**
     * 使用指定日期时间格式，将日期时间字符串转换成 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate parseToLocalDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = localDateTimeNow();
        System.out.println(format(localDateTime, UTC_FORMAT_PATTERN));
    }

}
