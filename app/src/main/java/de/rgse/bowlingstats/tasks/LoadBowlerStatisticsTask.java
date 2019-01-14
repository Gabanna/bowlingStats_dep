package de.rgse.bowlingstats.tasks;

import android.content.Context;

import de.rgse.bowlingstats.model.BowlerStatisticWrapper;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlerStatisticsTask extends ContextAwareTask<String, BowlerStatisticWrapper> {


    private LoadBowlerStatisticsTask(Callback<BowlerStatisticWrapper> callback) {
        super(callback);
    }

    @Override
    protected BowlerStatisticWrapper doInBackground(ContextAwareTaskParam<String>[] voids) {
        ContextAwareTaskParam<String> name = voids[0];
        return new BowlerStatisticWrapper(Database.getInstance(name.getContext()).bowlerDao().getStatistics(name.getParam()));
    }

    public static void loadBowlerStatistics(String name, Context context, Callback<BowlerStatisticWrapper> callback) {
        new LoadBowlerStatisticsTask(callback).execute(new ContextAwareTaskParam<>(context, name));
    }

    public static void loadBowlerStatistics(Context context, Callback<BowlerStatisticWrapper> callback) {
        new LoadBowlerStatisticsTask(callback).execute(new ContextAwareTaskParam<>(context, null));
    }
}
