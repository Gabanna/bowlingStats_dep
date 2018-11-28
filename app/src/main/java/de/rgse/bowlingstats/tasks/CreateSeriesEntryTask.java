package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.persistence.Database;

public class CreateSeriesEntryTask extends AsyncTask<SeriesEntry, Void, Void> {

    private Context context;
    private Callback<Void> callback;

    private CreateSeriesEntryTask(Context context, Callback<Void> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(Void bowlers) {
        callback.call(null);
    }

    @Override
    protected Void doInBackground(SeriesEntry... seriesEntries) {
        SeriesEntry seriesEntry = seriesEntries[0];
        Database.getInstance(context).seriesDao().insert(seriesEntry);
        return null;
    }

    public static void createSeriesEntry(SeriesEntry seriesToEnter, Context context, Callback<Void> callback) {
        new CreateSeriesEntryTask(context, callback).execute(seriesToEnter);
    }
}
