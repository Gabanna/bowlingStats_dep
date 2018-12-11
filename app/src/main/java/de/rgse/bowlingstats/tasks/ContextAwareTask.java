package de.rgse.bowlingstats.tasks;

import android.os.AsyncTask;

public abstract class ContextAwareTask<PARAM, RESULT> extends AsyncTask<ContextAwareTaskParam<PARAM>, Void, RESULT> {

    private Callback<RESULT> callback;

    protected ContextAwareTask(Callback<RESULT> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(RESULT param) {
        callback.call(param);
    }
}
