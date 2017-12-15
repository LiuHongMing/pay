package com.github.tiger.pay.web.filter;

import com.github.tiger.pay.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpServletRequestParameterWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> parameters = new LinkedHashMap<>(16);

    private final char delimiter = '_';

    public HttpServletRequestParameterWrapper(HttpServletRequest request) {
        super(request);
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            String[] values = parameterMap.get(key);
            parameters.put(StringUtil.hump(key, delimiter), values);
        }
    }

    public String getParameter(String name) {
        String[] arr = (name != null ? this.parameters.get(name) : null);
        return (arr != null && arr.length > 0 ? arr[0] : null);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return (name != null ? this.parameters.get(name) : null);
    }


    @Override
    public Map<String, String[]> getParameterMap() {
        return parameters;
    }

}
