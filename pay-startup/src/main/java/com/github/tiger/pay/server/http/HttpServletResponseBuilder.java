package com.github.tiger.pay.server.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * 构造 HttpServletResponse
 *
 * @author liuhongming
 * @date 2017-12-15
 */
public class HttpServletResponseBuilder {

    private static final Logger logger = LoggerFactory.getLogger(HttpServletResponseBuilder.class);

    public static MockHttpServletResponse build() {
        return new MockHttpServletResponse();
    }

}
