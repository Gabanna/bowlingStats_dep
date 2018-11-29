package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class LoadSeriesTask extends AsyncTask<Void, Void, List<Date>> {


    private Context context;
    private Callback<List<Date>> callback;

    private LoadSeriesTask(Context context, Callback<List<Date>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<Date> bowlers) {
        callback.call(bowlers);
    }

    @Override
    protected List<Date> doInBackground(Void... voids) {
        List<Date> entriyDates = Database.getInstance(context).seriesDao().getEntriyDates();
        Collections.sort(entriyDates);
        Collections.reverse(entriyDates);
        return entriyDates;
    }

    public static void loadSeries(Context context, Callback<List<Date>> callback) {
        new LoadSeriesTask(context, callback).execute();
    }
}
