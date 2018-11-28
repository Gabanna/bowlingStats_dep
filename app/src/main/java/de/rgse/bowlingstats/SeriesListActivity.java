package de.rgse.bowlingstats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        FabFactory.createFab(this, MaterialDrawableBuilder.IconValue.COUNTER).setOnClickListener(v -> openCreateSeries());

        seriesView.setOnItemClickListener((parent, view, position, id) -> {
            final Date date = (Date) seriesView.getAdapter().getItem((int) view.getTag());

            if (date != null) {
                openSeries(date);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4) {
            Intent intent = new Intent(getApplicationContext(), SeriesActivity.class);
            intent.setData(Uri.parse(data.getDataString()));
            startActivity(intent);
        }
    }

    private void openSeries(Date date) {
        Intent intent = new Intent(getApplicationContext(), SeriesActivity.class);
        intent.setData(Uri.parse(DateFormat.getDateInstance().format(date)));
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
        startActivityForResult(new Intent(getApplicationContext(), CreateSeriesActivity.class), 4);
    }
}
