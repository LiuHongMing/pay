package com.senyint.pay.constant.wxpay;

/**
 * 微信支付类型枚举
 */
public enum WxPayTypeEnum {

    JSAPI("公众号支付"),

    NATIVE("原生扫码支付"),

    APP("APP支付"),

    MICROPAY("刷卡支付");

    /**
     * 描述
     */
    private String desc;

    private WxPayTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
