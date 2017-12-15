package com.github.tiger.pay.server.http;

import com.github.tiger.pay.common.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HttpServletRequestBuilder
 * @Description: 构造HttpServletRequest
 */
public class HttpServletRequestBuilder {

    private static final Logger logger = LoggerFactory.getLogger(HttpServletRequestBuilder.class);

    private static final char PARAMETER_DELIMITER = '_';

    public static MockHttpServletRequest build(HttpRequest request) throws HttpRequestException {
        return wrap(request);
    }

    /**
     * 包装 HttpServletRequest
     */
    private static MockHttpServletRequest wrap(HttpRequest request) throws HttpRequestException {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        HttpMethod httpMethod = request.method();
        String uri = request.uri();
        HttpHeaders headers = request.headers();

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        // scheme
        if (uriComponents.getScheme() != null) {
            httpServletRequest.setScheme(uriComponents.getScheme());
        }
        // host
        if (uriComponents.getHost() != null) {
            httpServletRequest.setServerName(uriComponents.getHost());
        }
        // port
        if (uriComponents.getPort() != -1) {
            httpServletRequest.setServerPort(uriComponents.getPort());
        }
        // method
        httpServletRequest.setMethod(httpMethod.name());
        // uri
        httpServletRequest.setRequestURI(uriComponents.getPath());
        httpServletRequest.setPathInfo(uriComponents.getPath());
        // header
        for (String name : headers.names()) {
            httpServletRequest.addHeader(name, headers.get(name));
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> queryParams = queryStringDecoder.parameters();
        for (String key : queryParams.keySet()) {
            List<String> values = queryParams.get(key);
            httpServletRequest.addParameter(StringUtil.hump(key, PARAMETER_DELIMITER), values.toArray(new String[values.size()]));
        }
        if (httpMethod == HttpMethod.POST) { // POST
            // body
            HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(request);
            List<InterfaceHttpData> httpDatas = postRequestDecoder.getBodyHttpDatas();
            for (InterfaceHttpData httpData : httpDatas) {
                if (httpData instanceof Attribute) {
                    Attribute data = (Attribute) httpData;
                    try {
                        httpServletRequest.setParameter(StringUtil.hump(data.getName(), PARAMETER_DELIMITER), data.getValue());
                    } catch (IOException e) {
                        throw new HttpRequestException(e.getMessage(), e.getCause());
                    }
                }
            }
            ByteBuf content = ((HttpContent) request).content();
            if (content != null && content.isReadable()) {
                byte[] byteContent = new byte[content.readableBytes()];
                content.readBytes(byteContent);
                httpServletRequest.setContent(byteContent);
            }
        }
        return httpServletRequest;
    }

}
