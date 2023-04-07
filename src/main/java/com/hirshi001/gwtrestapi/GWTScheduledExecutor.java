package com.hirshi001.gwtrestapi;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Timer;
import com.hirshi001.restapi.ScheduledExec;
import com.hirshi001.restapi.TimerAction;

import java.util.concurrent.TimeUnit;

public class GWTScheduledExecutor implements ScheduledExec {

    private final Scheduler scheduler = Scheduler.get();
    @Override
    public void run(Runnable runnable, long delay) {
        Timer timer = new Timer() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        timer.schedule((int)delay);
    }

    @Override
    public void run(Runnable runnable, long delay, TimeUnit period) {
        Timer timer = new Timer() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        int delayMillis = (int) TimeUnit.MILLISECONDS.convert(delay, period);
        timer.schedule(delayMillis);
    }

    @Override
    public void runDeferred(Runnable runnable) {
        scheduler.scheduleDeferred(runnable::run);
    }

    @Override
    public TimerAction repeat(Runnable runnable, long initialDelay, long delay, TimeUnit period) {

        final Timer inner = new Timer() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        Timer timer = new Timer() {
            @Override
            public void run() {
                inner.scheduleRepeating((int)period.toMillis(delay));
            }
        };
        timer.schedule((int) period.toMillis(initialDelay));
        return new GWTTimerAction(timer, inner, initialDelay, delay, runnable);
    }


}
