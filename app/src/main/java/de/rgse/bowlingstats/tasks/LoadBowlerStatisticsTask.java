package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import de.rgse.bowlingstats.model.BowlerStatisticWrapper;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlerStatisticsTask extends AsyncTask<String, Void, BowlerStatisticWrapper> {


    private Context context;
    private Callback<BowlerStatisticWrapper> callback;

    private LoadBowlerStatisticsTask(Context context, Callback<BowlerStatisticWrapper> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(BowlerStatisticWrapper bowler) {
        callback.call(bowler);
    }

    @Override
    protected BowlerStatisticWrapper doInBackground(String... voids) {
        String name = voids[0];
        return new BowlerStatisticWrapper(Database.getInstance(context).bowlerDao().getStatistics(name));
    }

    public static void loadBowlerStatistics(String name, Context context, Callback<BowlerStatisticWrapper> callback) {
        new LoadBowlerStatisticsTask(context, callback).execute(name);
    }
}
