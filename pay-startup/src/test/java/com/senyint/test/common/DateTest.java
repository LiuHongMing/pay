package com.senyint.test.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class DateTest {

    private String pattern = "yyyyMMdd";

    @Test
    public void testJoda() {
        DateTime dateTime = DateTime.now().withTime(0, 0, 0, 0);
        System.out.println(dateTime.getMillis());
        System.out.println(DateTimeFormat.forPattern(pattern).print(dateTime));
    }
}
