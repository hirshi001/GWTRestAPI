package com.hirshi001.gwtrestapi;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Timer;
import com.hirshi001.restapi.RestFuture;
import com.hirshi001.restapi.RestFutureConsumer;
import com.hirshi001.restapi.RestFutureFactory;
import com.hirshi001.restapi.ScheduledExec;

import java.util.concurrent.*;

public class GWTRestFutureFactory implements RestFutureFactory {

    private static final ScheduledExec DEFAULT_EXECUTOR = new GWTScheduledExecutor();
    @Override
    public <T> RestFuture<T, T> create() {
        return create(DEFAULT_EXECUTOR, true, (RestFutureConsumer<T, T>) null);
    }

    @Override
    public <T> RestFuture<T, T> create(ScheduledExec executor) {
        return create(executor, true, (RestFutureConsumer<T, T>) null);
    }

    @Override
    public <T> RestFuture<T, T> create(RestFutureConsumer<T, T> consumer) {
        return create(DEFAULT_EXECUTOR, consumer);
    }

    @Override
    public <T> RestFuture<T, T> create(ScheduledExec executor, RestFutureConsumer<T, T> consumer) {
        return create(executor, true, consumer);
    }

    @Override
    public <T> RestFuture<T, T> create(ScheduledExec executor, boolean cancel, RestFutureConsumer<T, T> consumer) {
        return new GWTRestFuture<>(executor, cancel, consumer, null);
    }

    @Override
    public <T> RestFuture<T, T> create(Callable<T> action) {
        return create(DEFAULT_EXECUTOR, true, action);
    }

    @Override
    public <T> RestFuture<T, T> create(ScheduledExec executor, Callable<T> action) {
        return create(executor, true, action);
    }

    @Override
    public <T> RestFuture<T, T> create(ScheduledExec executor, boolean cancel, Callable<T> action) {
        return new GWTRestFuture<>(executor, cancel, (future, input) -> {
            try {
                T result = action.call();
                future.taskFinished(result);
            } catch (Exception e) {
                future.setCause(e);
            }
        }, null);
    }

    @Override
    public ScheduledExec getDefaultExecutor() {
        return DEFAULT_EXECUTOR;
    }
}
