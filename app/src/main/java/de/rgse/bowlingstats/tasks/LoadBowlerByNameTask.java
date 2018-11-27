package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlerByNameTask extends AsyncTask<String, Void, Bowler> {


    private Context context;
    private Callback<Bowler> callback;

    private LoadBowlerByNameTask(Context context, Callback<Bowler> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(Bowler bowler) {
        callback.call(bowler);
    }

    @Override
    protected Bowler doInBackground(String... voids) {
        String name = voids[0];
        return Database.getInstance(context).bowlerDao().getBowlerByName(name);
    }

    public static void loadBowlerByName(Context context, Callback<Bowler> callback) {
        new LoadBowlerByNameTask(context, callback).execute();
    }
}
