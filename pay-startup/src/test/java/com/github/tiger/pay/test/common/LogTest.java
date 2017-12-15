package com.github.tiger.pay.test.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testLevel() throws Exception {
        if (logger.isWarnEnabled()) {
            logger.warn("pay_monitor_warn");
        }
        if (logger.isErrorEnabled()) {
            logger.error("pay_monitor_error");
        }
    }
}
