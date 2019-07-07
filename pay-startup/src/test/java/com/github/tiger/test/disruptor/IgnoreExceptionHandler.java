package com.github.tiger.test.disruptor;

import com.lmax.disruptor.ExceptionHandler;

public class IgnoreExceptionHandler implements ExceptionHandler<MyEvent> {

    @Override
    public void handleEventException(Throwable ex, long sequence, MyEvent event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }

}
