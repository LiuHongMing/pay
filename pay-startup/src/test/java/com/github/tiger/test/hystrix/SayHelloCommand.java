package com.github.tiger.test.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

public class SayHelloCommand extends HystrixCommand<String> {

    final String name;

    protected SayHelloCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloServiceGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000)));
        this.name = name;
    }

    @Override
    protected String getFallback() {
        return String.format("[FallBack]Hello %s!", name);
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(600);
        return String.format("Hello %s!", name);
    }
}
