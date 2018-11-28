package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.BowlerStatistic;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlerStatisticsTask extends AsyncTask<String, Void, List<BowlerStatistic>> {


    private Context context;
    private Callback<List<BowlerStatistic>> callback;

    private LoadBowlerStatisticsTask(Context context, Callback<List<BowlerStatistic>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<BowlerStatistic> bowler) {
        callback.call(bowler);
    }

    @Override
    protected List<BowlerStatistic> doInBackground(String... voids) {
        String name = voids[0];
        return Database.getInstance(context).bowlerDao().getStatistics(name);
    }

    public static void loadBowlerStatistics(String name, Context context, Callback<List<BowlerStatistic>> callback) {
        new LoadBowlerStatisticsTask(context, callback).execute(name);
    }
}
