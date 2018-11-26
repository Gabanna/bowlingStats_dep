package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.Session;
import de.rgse.bowlingstats.persistence.Database;

public class LoadSessionsTask extends AsyncTask<Void, Void, List<Session>> {


    private Context context;
    private Callback<List<Session>> callback;

    private LoadSessionsTask(Context context, Callback<List<Session>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<Session> sessions) {
        callback.call(sessions);
    }

    @Override
    protected List<Session> doInBackground(Void... voids) {
        return Database.getInstance(context).sessionDao().getSessions();
    }

    public static final void loadSessions(Context context, Callback<List<Session>> callback) {
        new LoadSessionsTask(context, callback).execute();
    }
}
