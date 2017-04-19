package com.senyint.server.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @ClassName: HttpServletResponseBuilder
 * @Description: 构造HttpServletResponse
 */
public class HttpServletResponseBuilder {

    private static final Logger logger = LoggerFactory.getLogger(HttpServletResponseBuilder.class);

    public static MockHttpServletResponse build() {
        return new MockHttpServletResponse();
    }

}
