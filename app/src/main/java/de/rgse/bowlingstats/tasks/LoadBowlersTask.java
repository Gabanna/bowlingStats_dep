package de.rgse.bowlingstats.tasks;

import android.content.Context;

import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class LoadBowlersTask extends ContextAwareTask<Void, List<Bowler>> {


    private LoadBowlersTask(Callback<List<Bowler>> callback) {
        super(callback);
    }

    @Override
    protected List<Bowler> doInBackground(ContextAwareTaskParam<Void>[] voids) {
        ContextAwareTaskParam<Void> v = voids[0];
        return Database.getInstance(v.getContext()).bowlerDao().getBowlers();
    }

    public static void loadBowlers(Context context, Callback<List<Bowler>> callback) {
        new LoadBowlersTask(callback).execute(new ContextAwareTaskParam<>(context, null));
    }
}
