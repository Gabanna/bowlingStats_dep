package de.rgse.bowlingstats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rgse.bowlingstats.adapters.BowlerSeriesEntryAdapter;
import de.rgse.bowlingstats.factories.FabFactory;
import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.BowlerSeriesEntries;
import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.tasks.LoadBowlersTask;
import de.rgse.bowlingstats.tasks.LoadSeriesEntriesTask;

import static de.rgse.bowlingstats.CreateSeriesActivity.CREATE_SERIES_REQUEST_CODE;

public class SeriesActivity extends AppCompatActivity {

    private Map<String, Bowler> bowlers = new HashMap<>();
    private Date date;
    private ProgressBar progessBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        progessBar = findViewById(R.id.progressBar);
        progessBar.setIndeterminate(true);
        progessBar.setVisibility(View.VISIBLE);

        FabFactory.createFab(this, MaterialDrawableBuilder.IconValue.COUNTER).setOnClickListener(v -> openCreateSeries());

        loadBowlers();
        String dataString = getIntent().getDataString();

        if (dataString != null) {
            try {
                date = DateFormat.getDateInstance().parse(dataString);
                setTitle(dataString);

            } catch (ParseException e) {
                Log.e(SeriesActivity.class.getSimpleName(), "unable to parse date from intent", e);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSeries();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadSeries();
    }

    private void loadBowlers() {
        LoadBowlersTask.loadBowlers(getApplicationContext(), b -> {
            b.forEach(bowler -> bowlers.put(bowler.getId(), bowler));
            loadSeries();
        });
    }

    private void loadSeries() {
        progessBar.setVisibility(View.VISIBLE);
        LoadSeriesEntriesTask.loadSeries(date, getApplicationContext(), this::updateList);
    }

    private void updateList(List<SeriesEntry> entries) {
        Map<String, BowlerSeriesEntries> result = new HashMap<>();
        entries.forEach(entry -> {
            BowlerSeriesEntries bowlerSeriesEntries = result.computeIfAbsent(entry.getBowlerId(), bowlerId -> {
                Bowler bowler = bowlers.get(bowlerId);
                return new BowlerSeriesEntries(bowler);
            });
            bowlerSeriesEntries.addSeriesEntry(entry);
        });

        BowlerSeriesEntryAdapter adapter = new BowlerSeriesEntryAdapter(this, new ArrayList<>(result.values()));
        ListView listView = findViewById(R.id.serieEntriesView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            BowlerSeriesEntries item = adapter.getItem(position);
            Intent intent = new Intent(getApplicationContext(), EditSeriesActivity.class);
            intent.setData(Uri.parse(new Gson().toJson(item)));
            startActivity(intent);
        });

        if(progessBar.getVisibility() == View.VISIBLE) {
            progessBar.setVisibility(View.INVISIBLE);
        }
    }

    private void openCreateSeries() {
        Intent intent = new Intent(getApplicationContext(), CreateSeriesActivity.class);
        intent.setData(Uri.parse(DateFormat.getDateInstance().format(date)));
        startActivityForResult(intent, CREATE_SERIES_REQUEST_CODE);
    }
}
