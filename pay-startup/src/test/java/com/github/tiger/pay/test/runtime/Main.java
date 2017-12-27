package com.github.tiger.pay.test.runtime;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.management.ManagementFactory;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException,
            NoSuchFieldException, IllegalAccessException {

        String cp = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        // 配置进程属性: -Dfile.encoding=UTF-8
        final Process p = Runtime.getRuntime()
                .exec(new String[]{"java", "-Dfile.encoding=UTF-8", "-cp", cp,
                        "com.github.tiger.pay.test.runtime.SubMain"});

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                p.destroy();
            }
        });

        /**
         * the managed bean for the thread system of
         * the Java virtual machine.
         */
        for (long id : ManagementFactory.getThreadMXBean().getAllThreadIds()) {
            System.out.println(ManagementFactory.getThreadMXBean().getThreadInfo(id).getThreadName());
        }

        /**
         * the managed bean for the com.senyint.test.runtime system of
         * the Java virtual machine.
         */
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        logger.info("start process successful : pid/".concat(pid.toString()));

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        bw.write("向子进程输出信息");
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            logger.info(line);
        }
        br.close();
    }

}
