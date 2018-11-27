package de.rgse.bowlingstats;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.rgse.bowlingstats.adapters.BowlersAdapter;
import de.rgse.bowlingstats.factories.FabFactory;
import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.tasks.DeleteBowlerTask;
import de.rgse.bowlingstats.tasks.LoadBowlersTask;

import static de.rgse.bowlingstats.CreateBowler.CREATE_BOWLER_REQUEST_CODE;

public class BowlersActivity extends ToolbarActivity {

    private ListView bowlersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadBowlers();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowlers);

        FabFactory.createFab(this).setOnClickListener(view -> openCreateBowler());
        bowlersView = findViewById(R.id.bowlersView);
        updateList(new ArrayList<>());

        bowlersView.setOnItemClickListener((parent, view, position, id) -> {
            final Bowler bowler = (Bowler) bowlersView.getAdapter().getItem((int) view.getTag());

            if (bowler != null) {
                new AlertDialog.Builder(BowlersActivity.this)
                        .setTitle(getResources().getString(R.string.deleteBowler))
                        .setMessage(String.format(getResources().getString(R.string.doDeleteBowler), bowler.getName()))
                        .setPositiveButton(R.string.yes, (dialog, whichButton) -> deleteBowler(bowler))
                        .setNegativeButton(R.string.no, null).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CREATE_BOWLER_REQUEST_CODE) {
                loadBowlers();
            }
        }
    }

    private void deleteBowler(final Bowler bowlerToDelete) {
        DeleteBowlerTask.deleteBowler(bowlerToDelete, getApplicationContext(), voids -> {
            loadBowlers();
            Snackbar.make(bowlersView, String.format(getResources().getString(R.string.wasDeleted), bowlerToDelete.getName()), Snackbar.LENGTH_LONG).show();
        });
    }

    private void loadBowlers() {
        LoadBowlersTask.loadBowlers(getApplicationContext(), this::updateList);
    }

    private void updateList(List<Bowler> bowlers) {
        BowlersAdapter adapter = new BowlersAdapter(this, bowlers);
        bowlersView.setAdapter(adapter);
    }

    private void openCreateBowler() {
        startActivityForResult(new Intent(getApplicationContext(), CreateBowler.class), CREATE_BOWLER_REQUEST_CODE);
    }
}
