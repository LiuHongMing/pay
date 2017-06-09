package com.senyint.test.hystrix;

import java.util.concurrent.Future;

public class HelloService {

    public static String sayHello(final String name) {
        return new SayHelloCommand(name).execute();
    }

    public static Future sayHelloAsync(final String name) {
        return new SayHelloCommand(name).queue();
    }

    public static void main(String[] args) {
        System.out.println(HelloService.sayHello("World"));
    }
}
