package com.github.tiger.pay.common.dao.datasource;

/**
 * 存储、获取数据源上下文名称
 *
 * @author liuhongming
 */
public class DataSourceHolder {

    private static final ThreadLocal<String> dataSources = new ThreadLocal<>();

    public static String getDataSource() {
        return dataSources.get();
    }

    public static void setDataSourcee(String name) {
        dataSources.set(name);
    }

    public static void clearDataSource() {
        dataSources.remove();
    }
}
