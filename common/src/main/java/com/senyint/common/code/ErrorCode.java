package com.senyint.common.code;

/**
 * 错误码基类，实现CodeQualifier接口
 *
 * @author liuhongming
 */
public class ErrorCode implements CodeQualifier {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;


    public ErrorCode() {
    }

    public ErrorCode(String code, String msg) {
        this.code = code;
        this.message = msg;
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

    public static ErrorCode newErrorCode(String code, String msg) {
        return new ErrorCode(code, msg);
    }
}
