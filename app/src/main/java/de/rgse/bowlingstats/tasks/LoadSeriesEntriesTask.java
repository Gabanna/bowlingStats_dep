package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.persistence.Database;

public class LoadSeriesEntriesTask extends AsyncTask<Date, Void, List<SeriesEntry>> {


    private Context context;
    private Callback<List<SeriesEntry>> callback;

    private LoadSeriesEntriesTask(Context context, Callback<List<SeriesEntry>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<SeriesEntry> bowlers) {
        callback.call(bowlers);
    }

    @Override
    protected List<SeriesEntry> doInBackground(Date... dates) {
        Date date = dates[0];
        return Database.getInstance(context).seriesDao().getEntriesByDate(date);
    }

    public static void loadSeries(Context context, Callback<List<SeriesEntry>> callback) {
        new LoadSeriesEntriesTask(context, callback).execute();
    }
}
