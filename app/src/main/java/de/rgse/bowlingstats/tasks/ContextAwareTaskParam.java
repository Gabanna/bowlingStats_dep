package de.rgse.bowlingstats.tasks;

import android.content.Context;

public class ContextAwareTaskParam<T> {

    private Context context;
    private T param;

    public ContextAwareTaskParam(Context context, T param) {
        this.context = context;
        this.param = param;
    }

    public Context getContext() {
        return context;
    }

    public T getParam() {
        return param;
    }
}
