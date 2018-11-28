package de.rgse.bowlingstats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

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

public class SeriesActivity extends ToolbarActivity {

    private Map<String, Bowler> bowlers = new HashMap<>();
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        FabFactory.createFab(this, MaterialDrawableBuilder.IconValue.COUNTER).setOnClickListener(v -> openCreateSeries());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        loadSeries();
    }

    private void loadBowlers() {
        LoadBowlersTask.loadBowlers(getApplicationContext(), b -> {
            b.forEach(bowler -> bowlers.put(bowler.getId(), bowler));
            loadSeries();
        });
    }

    private void loadSeries() {
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
    }

    private void openCreateSeries() {
        Intent intent = new Intent(getApplicationContext(), CreateSeriesActivity.class);
        intent.setData(Uri.parse(DateFormat.getDateInstance().format(date)));
        startActivityForResult(intent, CREATE_SERIES_REQUEST_CODE);
    }
}
