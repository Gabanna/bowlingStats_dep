package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Calendar;
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

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date from = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date to = calendar.getTime();

        return Database.getInstance(context).seriesDao().getEntriesByDate(from, to);
    }

    public static void loadSeries(Date date, Context context, Callback<List<SeriesEntry>> callback) {
        new LoadSeriesEntriesTask(context, callback).execute(date);
    }
}
