package com.zhaoyouhua.spider.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zhoukc
 * @Description 时间获取和转换
 */
public class DateUtil {


    /**
     * @Description 转换成字符串
     * @author zhoukc
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * @Description 时间戳转换成日期
     * @author zhoukc
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * @Description 转换成时间超戳
     * @author zhoukc
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * @Description 格式化字符串时间
     * @author zhoukc
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * @Description 获取当天的0点
     * @author zhoukc
     */
    public static LocalDateTime getCurrentDayStart() {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return today_start;
    }

    /**
     * @Description 获取当天的24点
     * @author zhoukc
     */
    public static LocalDateTime getCurrentDayEnd() {
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return today_end;
    }

    /**
     * @Description 获取当前时间加多少天后的时间
     * @author zhoukc
     */
    public static LocalDateTime getCurrentDatePlusDay(long days) {
        return LocalDateTime.now().plusDays(days);
    }

    /**
     * @Description 得到相差天数
     * @author zhoukc
     */
    public static long getDifferDays(LocalDate start, LocalDate end) {
        return Math.abs(start.toEpochDay() - end.toEpochDay());
    }

    /**
     * @Description 得到相差天数
     * @author zhoukc
     */
    public static long getDifferDays(LocalDateTime start, LocalDateTime end) {
        return Math.abs(start.toLocalDate().toEpochDay() - end.toLocalDate().toEpochDay());
    }


    /**
     * 将LocalDate 转换成 Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将LocalDateTime 转换成 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将 Date 转换成LocalDate
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将 Date 转换成LocalDateTime
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    // public static Duration get
    // Duration duration = Duration.between(of1, of);
    //duration.toDays()
    //duration.toHours()

}
