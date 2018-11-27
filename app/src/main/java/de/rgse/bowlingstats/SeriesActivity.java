package de.rgse.bowlingstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import java.util.List;

import de.rgse.bowlingstats.factories.FabFactory;
import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.tasks.LoadSeriesEntriesTask;

import static de.rgse.bowlingstats.CreateSeriesActivity.CREATE_SERIES_REQUEST_CODE;

public class SeriesActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        FabFactory.createFab(this).setOnClickListener(v -> openCreateSeries());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        loadSeries();
    }

    private void loadSeries() {
        LoadSeriesEntriesTask.loadSeries(getApplicationContext(), this::updateList);
    }

    private void updateList(List<SeriesEntry> entries) {

    }

    private void openCreateSeries() {
        startActivityForResult(new Intent(getApplicationContext(), CreateSeriesActivity.class), CREATE_SERIES_REQUEST_CODE);
    }
}
