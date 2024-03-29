package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.persistence.Database;

public class LoadSeriesEntriesTask extends ContextAwareTask<Date, List<SeriesEntry>> {


    private LoadSeriesEntriesTask(Callback<List<SeriesEntry>> callback) {
        super(callback);
    }

    @Override
    protected List<SeriesEntry> doInBackground(ContextAwareTaskParam<Date>[] dates) {
        ContextAwareTaskParam<Date> date = dates[0];

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.getParam());

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date from = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date to = calendar.getTime();

        return Database.getInstance(date.getContext()).seriesDao().getEntriesByDate(from, to);
    }

    public static void loadSeries(Date date, Context context, Callback<List<SeriesEntry>> callback) {
        new LoadSeriesEntriesTask(callback).execute(new ContextAwareTaskParam<>(context, date));
    }
}
