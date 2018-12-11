package de.rgse.bowlingstats.tasks;

import android.content.Context;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.rgse.bowlingstats.persistence.Database;

public class LoadSeriesTask extends ContextAwareTask<Void, List<Date>> {


    private LoadSeriesTask(Callback<List<Date>> callback) {
        super(callback);
    }

    @Override
    protected List<Date> doInBackground(ContextAwareTaskParam<Void>[] voids) {
        ContextAwareTaskParam<Void> v = voids[0];
        List<Date> entriyDates = Database.getInstance(v.getContext()).seriesDao().getEntriyDates();
        Collections.sort(entriyDates);
        Collections.reverse(entriyDates);
        return entriyDates;
    }

    public static void loadSeries(Context context, Callback<List<Date>> callback) {
        new LoadSeriesTask(callback).execute(new ContextAwareTaskParam<>(context, null));
    }
}
