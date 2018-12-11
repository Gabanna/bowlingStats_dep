package de.rgse.bowlingstats.tasks;

import android.content.Context;
import android.os.AsyncTask;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlerByNameTask extends ContextAwareTask<String, Bowler> {


    private LoadBowlerByNameTask(Callback<Bowler> callback) {
        super(callback);
    }

    @Override
    protected Bowler doInBackground(ContextAwareTaskParam<String>[] voids) {
        ContextAwareTaskParam<String> name = voids[0];
        return Database.getInstance(name.getContext()).bowlerDao().getBowlerByName(name.getParam());
    }

    public static void loadBowlerByName(String name, Context context, Callback<Bowler> callback) {
        new LoadBowlerByNameTask(callback).execute(new ContextAwareTaskParam<>(context, name));
    }
}
