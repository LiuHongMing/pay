package com.github.tiger.pay.constant;

/**
 * 交易类型枚举
 */
public enum TradeTypeEnum {

    EXPENSE("消费"),

    DEPOSIT("充值"),

    WITHDRAW("提现");

    /** 描述 */
    private String desc;

    private TradeTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
