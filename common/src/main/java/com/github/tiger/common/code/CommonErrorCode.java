package com.github.tiger.common.code;

/**
 * 公共错误码，非业务错误码
 *
 * @author liuhongming
 */
public class CommonErrorCode extends ErrorCode {

    /**
     * 接口调用成功
     */
    public static final ErrorCode SUCCESS = newErrorCode("10000", "接口调用成功");

    /**
     * 服务不可用
     */
    public static final ErrorCode SERVICE_UNAVAILABLE = newErrorCode("20000", "服务不可用");

    /**
     * 授权权限不足
     */
    public static final ErrorCode INVALID_AUTH = newErrorCode("20001", "授权权限不足");

    /**
     * 缺少必填参数
     */
    public static final ErrorCode MISS_REQUIRED_ARGUMENT = newErrorCode("40001", "缺少必填参数");

    /**
     * 非法参数
     */
    public static final ErrorCode ILLEGAL_ARGUMENT = newErrorCode("40002", "非法参数");

    /**
     * 业务处理失败
     */
    public static final ErrorCode BIZ_HANDLE_FAILURE = newErrorCode("40004", "业务处理失败");

    /**
     * 权限不足
     */
    public static final ErrorCode INSUFFICIENT_PERMISSIONS = newErrorCode("40006", "权限不足");

}
