package com.github.tiger.common.exception;

/**
 * 数据访问异常基类，所有数据访问异常都必须继承于此类
 *
 * @author liuhongming
 */
public class DAOException extends Exception {

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

}
