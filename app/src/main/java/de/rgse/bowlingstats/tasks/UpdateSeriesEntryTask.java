package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.persistence.Database;

public class UpdateSeriesEntryTask extends ContextAwareTask<SeriesEntry, Void> {

    private UpdateSeriesEntryTask(Callback<Void> callback) {
        super(callback);
    }

    @Override
    protected Void doInBackground(ContextAwareTaskParam<SeriesEntry>[] seriesEntries) {
        ContextAwareTaskParam<SeriesEntry> seriesEntry = seriesEntries[0];
        Database.getInstance(seriesEntry.getContext()).seriesDao().update(seriesEntry.getParam());
        return null;
    }

    public static void updateSeriesEntry(SeriesEntry seriesToEnter, Context context, Callback<Void> callback) {
        new UpdateSeriesEntryTask(callback).execute(new ContextAwareTaskParam<>(context, seriesToEnter));
    }
}
