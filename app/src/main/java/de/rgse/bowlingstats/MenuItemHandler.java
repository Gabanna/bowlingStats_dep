package de.rgse.bowlingstats;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.rgse.bowlingstats.persistence.DataImporter;

public class MenuItemHandler {

    private final Context context;

    public MenuItemHandler(Context context) {
        this.context = context;
    }

    public void filterMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            switch (item.getItemId()) {
                case R.id.navigation_bowlers:
                    if (BowlersActivity.class.isInstance(context)) {
                        menu.removeItem(R.id.navigation_bowlers);
                    }
                    break;
                case R.id.navigation_series:
                    if (SeriesListActivity.class.isInstance(context)) {
                        menu.removeItem(R.id.navigation_series);
                    }
                    break;
                case R.id.navigation_statistics:
                    if (StatisticBowlerActivity.class.isInstance(context)) {
                        menu.removeItem(R.id.navigation_statistics);
                    }
                    break;
            }
        }
    }

    public boolean handleItemSelect(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.navigation_bowlers:
                if (!(BowlersActivity.class.isInstance(this))) {
                    openBowlers();
                }
                result = true;
                break;
            case R.id.navigation_series:
                if (!(SeriesListActivity.class.isInstance(this))) {
                    openSeriesList();
                }
                result = true;
                break;
            case R.id.navigation_statistics:
                if (!(StatisticBowlerActivity.class.isInstance(this))) {
                    openStatistics();
                }
                result = true;
                break;
            case R.id.navigation_import:
                startImport(item.getActionView());
                result = true;
                break;
        }

        return result;
    }

    private void openStatistics() { context.startActivity(new Intent(context, StatisticBowlerActivity.class)); }

    private void openBowlers() {
        context.startActivity(new Intent(context, BowlersActivity.class));
    }

    private void openSeriesList() {
        context.startActivity(new Intent(context, SeriesListActivity.class));
    }

    private void startImport(View view) { DataImporter.importDataFromOurBowlingScores(view); }
}
