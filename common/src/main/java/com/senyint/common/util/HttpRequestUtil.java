package com.senyint.common.util;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

public class HttpRequestUtil {

    private static MapJoiner queryStringJoiner = Joiner.on("&").withKeyValueSeparator("=");

    public static Map getParameterMap(HttpServletRequest request) {
        Map parameterMap = Maps.newHashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String fieldName = parameterNames.nextElement();
            String fieldValue = request.getParameter(fieldName);
            parameterMap.put(fieldName, fieldValue);
        }
        return parameterMap;
    }

    public static String joinParams(Map params) {
        return queryStringJoiner.join(params);
    }

    public static String getFullString(HttpServletRequest request) {
        String requestURL = request.getScheme() + "://" +
                request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
        Map<String, String> paramMap = HttpRequestUtil.getParameterMap(request);
        String requestInfo = String.format("%s?%s", requestURL, joinParams(paramMap));
        return requestInfo;
    }

    public static String getUriString(HttpServletRequest request) {
        String requestURL = request.getRequestURI();
        Map<String, String> paramMap = HttpRequestUtil.getParameterMap(request);
        String requestInfo = String.format("%s?%s", requestURL, joinParams(paramMap));
        return requestInfo;
    }

    public static void iterateParams(HttpServletRequest request) {
        iterateParams(request, null);
    }

    public static void iterateParams(HttpServletRequest request, RequestCommand cmd) {
        Map<String, String[]> requestParams = request.getParameterMap();
        if (cmd == null) {
            Map<String, String[]> requestParamsWrap = Maps.newHashMap();
            requestParamsWrap.putAll(requestParams);
            command.execute(requestParamsWrap);
            return;
        }
    }

    public interface RequestCommand {
        void execute(Map<String, String[]> requestParams);
    }

    private static final RequestCommand command = new DelimiterRequestCommand();

    public static class DelimiterRequestCommand implements RequestCommand {
        @Override
        public void execute(Map<String, String[]> requestParams) {
        }
    }
}
