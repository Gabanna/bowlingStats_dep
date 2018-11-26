package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlersTask extends AsyncTask<Void, Void, List<Bowler>> {


    private Context context;
    private Callback<List<Bowler>> callback;

    private LoadBowlersTask(Context context, Callback<List<Bowler>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<Bowler> bowlers) {
        callback.call(bowlers);
    }

    @Override
    protected List<Bowler> doInBackground(Void... voids) {
        return Database.getInstance(context).bowlerDao().getBowlers();
    }

    public static final void loadBowlers(Context context, Callback<List<Bowler>> callback) {
        new LoadBowlersTask(context, callback).execute();
    }
}
