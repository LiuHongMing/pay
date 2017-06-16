package com.senyint.pay.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpServletRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequestParameterWrapper parameterWrapper =
                new HttpServletRequestParameterWrapper((HttpServletRequest) request);
        chain.doFilter(parameterWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
