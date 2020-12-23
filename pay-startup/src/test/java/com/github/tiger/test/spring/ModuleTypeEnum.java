package com.github.tiger.test.spring;

import java.util.Arrays;

public enum ModuleTypeEnum {

    CUSTOMER(0, "customer", "人群");

    private Integer value;
    private String name;
    private String description;

    ModuleTypeEnum(Integer value, String name, String description) {
        this.value = value;
        this.name = name;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 根据value获取活动二级模块类型
     *
     * @param value
     * @return
     */
    public static ModuleTypeEnum getByValue(Integer value) {
        if (value == null)
            return null;
        return Arrays.stream(ModuleTypeEnum.values())
            .filter(item -> item.value.equals(value))
            .findFirst()
            .orElse(null);
    }
}
