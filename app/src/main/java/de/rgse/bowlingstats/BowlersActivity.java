package de.rgse.bowlingstats;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.ListView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

import de.rgse.bowlingstats.adapters.BowlersAdapter;
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

        createFab().setOnClickListener(view -> openCreateBowler());
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

    private FloatingActionButton createFab() {
        Drawable yourDrawable = MaterialDrawableBuilder.with(getApplicationContext()) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.ACCOUNT_PLUS) // provide an icon
                .setToActionbarSize() // set the icon size
                .setColor(Color.WHITE)
                .build(); // Finally call build
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(yourDrawable);
        return fab;
    }

    private void openCreateBowler() {
        startActivityForResult(new Intent(getApplicationContext(), CreateBowler.class), CREATE_BOWLER_REQUEST_CODE);
    }
}
