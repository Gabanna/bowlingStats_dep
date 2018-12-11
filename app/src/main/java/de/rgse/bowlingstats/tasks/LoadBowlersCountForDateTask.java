package de.rgse.bowlingstats.tasks;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlersCountForDateTask extends ContextAwareTask<Date, Long> {

    private LoadBowlersCountForDateTask(Callback<Long> callback) {
        super(callback);
    }

    @Override
    protected Long doInBackground(ContextAwareTaskParam<Date>[] dates) {
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

        return Database.getInstance(date.getContext()).seriesDao().getBowlerCountByDate(from, to);
    }

    public static void loadBowlersCountForDate(Date date, Context context, Callback<Long> callback) {
        new LoadBowlersCountForDateTask(callback).execute(new ContextAwareTaskParam<>(context, date));
    }

}
