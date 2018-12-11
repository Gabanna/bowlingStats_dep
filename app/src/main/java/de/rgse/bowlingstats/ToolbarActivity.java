package de.rgse.bowlingstats;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

public class ToolbarActivity extends AppCompatActivity {

    private MenuItemHandler itemHandler = new MenuItemHandler(this);


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater
                .with(this)
                .setDefaultColor(Color.WHITE)
                .inflate(R.menu.navigation, menu);

        itemHandler.filterMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = itemHandler.handleItemSelect(item);
        return result ? result : super.onOptionsItemSelected(item);
    }


}
