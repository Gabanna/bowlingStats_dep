package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.BowlerStatistic;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlerStatisticsTask extends AsyncTask<String, Void, BowlerStatistic> {


    private Context context;
    private Callback<BowlerStatistic> callback;

    private LoadBowlerStatisticsTask(Context context, Callback<BowlerStatistic> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(BowlerStatistic bowler) {
        callback.call(bowler);
    }

    @Override
    protected BowlerStatistic doInBackground(String... voids) {
        String name = voids[0];
        return Database.getInstance(context).bowlerDao().getStatistics(name);
    }

    public static void loadBowlerStatistics(String name, Context context, Callback<BowlerStatistic> callback) {
        new LoadBowlerStatisticsTask(context, callback).execute(name);
    }
}
