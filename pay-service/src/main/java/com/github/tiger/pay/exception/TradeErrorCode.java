package com.github.tiger.pay.exception;

import com.github.tiger.pay.common.code.ErrorCode;

/**
 * 交易错误码类
 *
 * @author liuhongming
 */
public class TradeErrorCode extends ErrorCode {

    //***************************** 公共 *****************************

    /**
     * 系统错误
     */
    public static final ErrorCode SYSTEM_ERROR = newErrorCode("SYSTEM_ERROR", "系统错误");

    /**
     * 参数无效
     */
    public static final ErrorCode INVALID_PARAMETER = newErrorCode("INVALID_PARAMETER", "参数无效");



    //***************************** 交易查询 *****************************

    /**
     * 查询的交易不存在
     */
    public static final ErrorCode TRADE_NOT_EXIST = newErrorCode("TRADE_NOT_EXIST", "查询的交易不存在");

    //***************************** 交易下单 *****************************

    /**
     * APP_ID填写错误
     */
    public static final ErrorCode PARTNER_ERROR = newErrorCode("PARTNER_ERROR", "APP_ID填写错误");
}
