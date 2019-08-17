package com.github.tiger.test.concurrent.interrupt;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Digital {

    Random random = new Random();

    public String call() throws InterruptedException {

        TimeUnit.SECONDS.sleep(2);

        int count = random.nextInt(10);
        int total = 0;
        for (int i = 0; i < count; i++) {
            total += random.nextInt(10);
        }
        if (count == 0) {
            count = 1;
        }
        double avg = (double) total / count;

        return String.format("%.2f", avg);
    }

}

