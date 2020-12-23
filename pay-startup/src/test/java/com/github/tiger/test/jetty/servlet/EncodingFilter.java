package com.github.tiger.test.jetty.servlet;

import org.springframework.web.filter.CharacterEncodingFilter;

public class EncodingFilter extends CharacterEncodingFilter {

    public EncodingFilter() {
        setEncoding("UTF-8");
    }
}
