package com.github.tiger.pay.rpc;

import java.io.Serializable;

/**
 * 封装RPC返回的结果
 *
 * @param <T>
 */
public class Result<T> implements Serializable {

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 返回结果
     */
    private T value;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 异常堆栈信息
     */
    private Throwable throwable;

    /**
     * 构造正常结果
     *
     * @param value
     */
    public Result(T value) {
        this(true, value, null, null, null);
    }

    /**
     * 构造异常结果
     *
     * @param code
     * @param message
     */
    public Result(String code, String message) {
        this(false, null, code, message, null);
    }

    /**
     * 构造完整结果
     *
     * @param isSuccess
     * @param value
     * @param code
     * @param message
     * @param throwable
     */
    public Result(boolean isSuccess, T value, String code, String message, Throwable throwable) {
        this.isSuccess = isSuccess;
        this.value = value;
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "Result{" +
                "isSuccess=" + isSuccess +
                ", value=" + value +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                '}';
    }
}
