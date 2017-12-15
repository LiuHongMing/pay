package com.github.tiger.pay.constant.wxpay;

/**
 * 微信交易状态枚举
 */
public enum WxTradeStatusEnum {

    SUCCESS("成功"),

    FAIL("失败");

    /**
     * 描述
     */
    private String desc;

    private WxTradeStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
