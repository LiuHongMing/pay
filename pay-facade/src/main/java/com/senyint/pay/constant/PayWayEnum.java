package com.senyint.pay.constant;

/**
 * 支付方式枚举
 */
public enum PayWayEnum {

    WEIXIN("微信"),

    ALIPAY("支付宝");

    /** 描述 */
    private String desc;

    private PayWayEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
