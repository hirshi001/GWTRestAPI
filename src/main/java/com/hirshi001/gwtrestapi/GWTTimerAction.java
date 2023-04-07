package com.hirshi001.gwtrestapi;

import com.google.gwt.user.client.Timer;
import com.hirshi001.restapi.TimerAction;

public class GWTTimerAction extends TimerAction {
    private final Timer timer, inner;

    public GWTTimerAction(Timer timer, Timer inner, long initialDelay, long delay, Runnable runnable) {
        super(initialDelay, delay, runnable);
        this.timer = timer;
        this.inner = inner;
    }

    @Override
    public void cancel() {
        super.cancel();
        timer.cancel();
        inner.cancel();
    }

}
