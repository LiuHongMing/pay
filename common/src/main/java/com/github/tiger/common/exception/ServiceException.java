package com.github.tiger.common.exception;

import com.github.tiger.common.code.ErrorCode;

/**
 * 服务异常基类，所有服务异常都必须继承于此类
 *
 * @author liuhongming
 */
public class ServiceException extends Exception {

    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

