package de.rgse.bowlingstats.tasks;

import android.content.Context;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class DeleteBowlerTask extends ContextAwareTask<Bowler, Void> {

    private DeleteBowlerTask(Callback<Void> callback) {
        super(callback);
    }

    @Override
    protected Void doInBackground(ContextAwareTaskParam<Bowler>[] bowlers) {
        ContextAwareTaskParam<Bowler> bowler = bowlers[0];
        Database.getInstance(bowler.getContext()).bowlerDao().delete(bowler.getParam());
        return null;
    }

    public static void deleteBowler(Bowler bowlerToDelete, Context context, Callback<Void> callback) {
        new DeleteBowlerTask(callback).execute(new ContextAwareTaskParam<>(context, bowlerToDelete));
    }
}
