package de.rgse.bowlingstats;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater
                .with(this)
                .setDefaultColor(Color.WHITE)
                .inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_bowlers:
                openBowlers();
                break;
            case R.id.navigation_series:
                openSeriesList();
                break;
        }
        return true;
    }

    private void openBowlers() {
        startActivity(new Intent(this, BowlersActivity.class));
    }
    private void openSeriesList() {
        startActivity(new Intent(this, SeriesListActivity.class));
    }

}
