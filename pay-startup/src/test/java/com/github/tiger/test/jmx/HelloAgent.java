package com.github.tiger.test.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author liuhongming
 */
public class HelloAgent {

    public static void main(String[] args)
            throws MalformedObjectNameException, NotCompliantMBeanException,
            InstanceAlreadyExistsException, MBeanRegistrationException {

        String domainName = "jmxBean";

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName helloName = new ObjectName(domainName + ":name=helloName");

        mBeanServer.registerMBean(new HelloMetric(), helloName);

        for (; ; ) {

        }
    }

}
