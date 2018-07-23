package com.github.tiger.test.jmx;

/**
 * @author liuhongming
 */
public class HelloMetric implements HelloMetricMBean, MBeanInfo {

    @Override
    public String getName() {
        return HelloMetric.class.getCanonicalName();
    }

    @Override
    public int getCount() {
        return 0;
    }

}
