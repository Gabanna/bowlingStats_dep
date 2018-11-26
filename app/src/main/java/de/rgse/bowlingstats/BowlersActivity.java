package de.rgse.bowlingstats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

import de.rgse.bowlingstats.adapters.BowlersAdapter;
import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.tasks.Callback;
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

        createFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateBowler();
            }
        });

        bowlersView = findViewById(R.id.bowlersView);
        updateList(new ArrayList<Bowler>());
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
        DeleteBowlerTask.deleteBowler(bowlerToDelete, getApplicationContext(), new Callback<Void>() {
            @Override
            public void call(Void voids) {
                loadBowlers();
                Snackbar.make(bowlersView, String.format(getResources().getString(R.string.wasDeleted), bowlerToDelete.getName()), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBowlers() {
        LoadBowlersTask.loadBowlers(getApplicationContext(), new Callback<List<Bowler>>() {
            @Override
            public void call(List<Bowler> arguments) {
                updateList(arguments);
            }
        });
    }

    private void updateList(List<Bowler> bowlers) {
        BowlersAdapter adapter = new BowlersAdapter(this, bowlers) {
            @Override
            public void onDelete(Bowler bowler) {
                deleteBowler(bowler);
            }
        };
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
