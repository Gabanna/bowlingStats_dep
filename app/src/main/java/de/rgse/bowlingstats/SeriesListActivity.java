package de.rgse.bowlingstats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.rgse.bowlingstats.adapters.SeriesListAdapter;
import de.rgse.bowlingstats.factories.FabFactory;
import de.rgse.bowlingstats.tasks.LoadSeriesTask;

public class SeriesListActivity extends ToolbarActivity {

    private ListView seriesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSeries();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_list);

        seriesView = findViewById(R.id.seriesView);
        updateList(new ArrayList<>());

        FabFactory.createFab(this).setOnClickListener(v -> openCreateSeries());

        seriesView.setOnItemClickListener((parent, view, position, id) -> {
            final Date dateTime = (Date) seriesView.getAdapter().getItem((int) view.getTag());

            if (dateTime != null) {
                openSeries(dateTime);
            }
        });
    }

    private void openSeries(Date dateTime) {
        Intent intent = new Intent(getApplicationContext(), SeriesActivity.class);
        intent.setData(Uri.parse(DateFormat.getDateTimeInstance().format(dateTime)));
        startActivity(intent);
    }

    private void updateList(List<Date> dates) {
        SeriesListAdapter adapter = new SeriesListAdapter(this, dates);
        seriesView.setAdapter(adapter);
    }

    private void loadSeries() {
        LoadSeriesTask.loadSeries(getApplicationContext(), this::updateList);
    }

    private void openCreateSeries() {
        startActivity(new Intent(getApplicationContext(), CreateSeriesActivity.class));
    }
}
