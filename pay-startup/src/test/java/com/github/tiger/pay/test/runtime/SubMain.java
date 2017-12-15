package com.github.tiger.pay.test.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class SubMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 测试父进程创建子进程
        // FileOutputStream fos = new FileOutputStream("f:/sub.txt");
        // fos.close();

        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println("start sub process successful : pid/".concat(pid));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("由父进程输入的信息 : " + line);
        }
        br.close();

        TimeUnit.SECONDS.sleep(10);
    }

}
