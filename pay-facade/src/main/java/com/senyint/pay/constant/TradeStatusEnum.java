package com.senyint.pay.constant;

/**
 * 交易状态枚举
 */
public enum TradeStatusEnum {

    /**
     * 交易成功
     */
    SUCCESS("交易成功"),

    /**
     * 交易失败
     */
    FAILED("交易失败"),

    /**
     * 订单已创建
     */
    CREATED("订单已创建"),

    /**
     * 订单已取消
     */
    CANCELED("订单已取消"),

    /**
     * 等待支付
     */
    WAITING_PAYMENT("等待支付");

    /** 描述 */
    private String desc;

    private TradeStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
