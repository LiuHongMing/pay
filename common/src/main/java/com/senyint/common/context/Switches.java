package com.senyint.common.context;

/**
 * 交换器枚举类
 */
public enum Switches {

    GLOBAL(30 * 60 * 1000);

    private int expireInSeconds;

    Switches(int expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }

    public int getExpireInSeconds() {
        return expireInSeconds;
    }

    public void setExpireInSeconds(int expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }

}
