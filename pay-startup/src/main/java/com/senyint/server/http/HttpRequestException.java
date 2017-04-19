package com.senyint.server.http;

/**
 * Http请求异常
 *
 * @author liuhongming
 */
public class HttpRequestException extends Exception {

    /**
     * 参数异常
     */
    public static final HttpRequestException PARAMETER_ERROR = new HttpRequestException(
            20001, "参数异常");

    /**
     * 异常信息
     */
    protected String message;

    /**
     * 错误码
     */
    protected int code;

    public HttpRequestException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.message = String.format(msgFormat, args);
    }

    public HttpRequestException() {
        super();
    }

    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestException(Throwable cause) {
        super(cause);
    }

}
