package de.rgse.bowlingstats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import de.rgse.bowlingstats.adapters.MenuItemAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ListView menuView = findViewById(R.id.menuView);

        MaterialMenuInflater
                .with(this)
                .setDefaultColor(Color.WHITE)
                .inflate(R.menu.navigation, menu);

        List<MenuItem> items = new ArrayList<>();
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            items.add(item);
        }

        MenuItemAdapter adapter = new MenuItemAdapter(this, items);
        menuView.setAdapter(adapter);
        menuView.setOnItemClickListener((parent, view, position, id) -> {
        });

        return false;
    }
}
