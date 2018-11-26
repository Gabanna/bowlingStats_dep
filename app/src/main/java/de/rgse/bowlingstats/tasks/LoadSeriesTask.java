package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.rgse.bowlingstats.model.Series;
import de.rgse.bowlingstats.model.Session;
import de.rgse.bowlingstats.persistence.Database;

public class LoadSeriesTask extends AsyncTask<Session, Void, List<Series>> {


    private Context context;
    private Callback<List<Series>> callback;

    private LoadSeriesTask(Context context, Callback<List<Series>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<Series> series) {
        callback.call(series);
    }

    @Override
    protected List<Series> doInBackground(Session... sessions) {
        Session session = sessions[0];
        if (session != null) {
            return Database.getInstance(context).seriesDao().getSeriesBySession(session.getId());
        } else {
            return null;
        }
    }

    public static final void loadSeries(Context context, Callback<List<Series>> callback) {
        new LoadSeriesTask(context, callback).execute();
    }
}
