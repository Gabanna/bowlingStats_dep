package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlerByIdTask extends AsyncTask<String, Void, Bowler> {


    private Context context;
    private Callback<Bowler> callback;

    private LoadBowlerByIdTask(Context context, Callback<Bowler> callback) {
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
        return Database.getInstance(context).bowlerDao().getBowlerById(name);
    }

    public static void loadBowlerById(String name, Context context, Callback<Bowler> callback) {
        new LoadBowlerByIdTask(context, callback).execute(name);
    }
}
