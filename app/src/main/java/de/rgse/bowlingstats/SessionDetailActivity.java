package de.rgse.bowlingstats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

import de.rgse.bowlingstats.adapters.BowlersAdapter;
import de.rgse.bowlingstats.adapters.SeriesAdapter;
import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.Series;
import de.rgse.bowlingstats.model.Session;
import de.rgse.bowlingstats.tasks.Callback;
import de.rgse.bowlingstats.tasks.LoadBowlersTask;
import de.rgse.bowlingstats.tasks.LoadSeriesTask;

public class SessionDetailActivity extends ToolbarActivity {

    private Session session;
    private TextView nameTextView;
    private ListView listView;
    private List<Bowler> bowlers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadBowlers();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);
        nameTextView = findViewById(R.id.sessionId);
        listView = findViewById(R.id.seriesView);

        createFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog().show();
            }
        });

        Intent intent = getIntent();
        String dataString = intent.getDataString();
        if (null != dataString) {
            session = new Gson().fromJson(dataString, Session.class);
            nameTextView.setText(session.getId());
        }
    }

    private void loadBowlers() {
        LoadBowlersTask.loadBowlers(getApplicationContext(), new Callback<List<Bowler>>() {
            @Override
            public void call(List<Bowler> arguments) {
                bowlers.clear();
                bowlers.addAll(arguments);
            }
        });
    }

    private AlertDialog.Builder initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SessionDetailActivity.this)
                .setTitle("new Series");

        ArrayAdapter bowlersAdapter = new ArrayAdapter<Bowler>(this, android.R.layout.simple_list_item_1, bowlers);
        bowlersAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        final View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_series_entry, listView, false);
        final EditText score = inflate.findViewById(R.id.score);
        final Spinner spinner = inflate.findViewById(R.id.bowlers);
        spinner.setAdapter(bowlersAdapter);

        builder.setView(inflate);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder;
    }

    private FloatingActionButton createFab() {
        Drawable yourDrawable = MaterialDrawableBuilder.with(getApplicationContext()) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.COUNTER) // provide an icon
                .setToActionbarSize() // set the icon size
                .setColor(Color.WHITE)
                .build(); // Finally call build
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(yourDrawable);
        return fab;
    }

    private void updateSeries(List<Series> series) {
        SeriesAdapter seriesAdapter = new SeriesAdapter(this, series);
        listView.setAdapter(seriesAdapter);
    }

    private void loadSeries(Session session) {
        LoadSeriesTask.loadSeries(getApplicationContext(), new Callback<List<Series>>() {
            @Override
            public void call(List<Series> arguments) {
                updateSeries(arguments);
            }
        });
    }
}
