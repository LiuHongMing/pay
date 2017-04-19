package com.senyint.pay.exception;

import com.senyint.common.code.ErrorCode;
import com.senyint.common.exception.ServiceException;

/**
 * 交易业务异常类
 *
 * @author liuhongming
 */
public class TradeException extends ServiceException {

    public TradeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TradeException() {
        super();
    }

    public TradeException(String message) {
        super(message);
    }

    public TradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeException(Throwable cause) {
        super(cause);
    }

}
