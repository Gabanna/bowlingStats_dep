package de.rgse.bowlingstats.tasks;

import android.content.Context;

import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.persistence.Database;

public class CreateSeriesEntryTask extends ContextAwareTask<SeriesEntry, Void> {

    private CreateSeriesEntryTask(Callback<Void> callback) {
        super(callback);
    }

    @Override
    protected Void doInBackground(ContextAwareTaskParam<SeriesEntry>[] seriesEntries) {
        ContextAwareTaskParam<SeriesEntry> param = seriesEntries[0];
        Database.getInstance(param.getContext()).seriesDao().insert(param.getParam());
        return null;
    }

    public static void createSeriesEntry(SeriesEntry seriesToEnter, Context context, Callback<Void> callback) {
        new CreateSeriesEntryTask(callback).execute(new ContextAwareTaskParam<SeriesEntry>(context, seriesToEnter));
    }
}
