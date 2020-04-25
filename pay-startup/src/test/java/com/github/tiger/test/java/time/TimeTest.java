package com.github.tiger.test.java.time;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@RunWith(JUnit4.class)
public class TimeTest {

    @Test
    public void testConvert() {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        Date today = Date.from(nowLocalDateTime.atOffset(
                ZoneOffset.ofHours(8)).toInstant());
        System.out.println(today);

        LocalDateTime localDateTimeAtZoneOffset = today.toInstant()
                .atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        System.out.println(localDateTimeAtZoneOffset.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        LocalDateTime localDateTimeAtZoneId = today.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(localDateTimeAtZoneId.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINESE)));
    }

}
