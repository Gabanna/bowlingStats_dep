package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class DeleteBowlerTask extends AsyncTask<Bowler, Void, Void> {

    private Context context;
    private Callback<Void> callback;

    private DeleteBowlerTask(Context context, Callback<Void> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(Void bowlers) {
        callback.call(null);
    }

    @Override
    protected Void doInBackground(Bowler... bowlers) {
        Bowler bowler = bowlers[0];
        Database.getInstance(context).bowlerDao().delete(bowler);
        return null;
    }

    public static void deleteBowler(Bowler bowlerToDelete, Context context, Callback<Void> callback) {
        new DeleteBowlerTask(context, callback).execute(bowlerToDelete);
    }
}
