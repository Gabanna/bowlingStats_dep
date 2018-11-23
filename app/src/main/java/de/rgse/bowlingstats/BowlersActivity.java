package de.rgse.bowlingstats;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

import static de.rgse.bowlingstats.CreateBowler.CREATE_BOWLER_REQUEST_CODE;

public class BowlersActivity extends AppCompatActivity {

    private ListView bowlersView;
    private List<String> bowlers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LoadBowlersTask().execute();

        setContentView(R.layout.activity_bowlers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateBowler();
            }
        });

        bowlersView = (ListView) findViewById(R.id.bowlersView);
        updateList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CREATE_BOWLER_REQUEST_CODE) {
                handleNewBowler(data);
            }
        }
    }

    private void handleNewBowler(Intent data) {
        Bowler bowler = new Gson().fromJson(data.getData().toString(), Bowler.class);
        this.bowlers.add(bowler.getName());
        updateList();
    }

    private void openCreateBowler() {
        startActivityForResult(new Intent(getApplicationContext(), CreateBowler.class), CREATE_BOWLER_REQUEST_CODE);
    }

    private class LoadBowlersTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected void onPostExecute(List<String> strings) {
            bowlers.clear();
            bowlers.addAll(strings);
            updateList();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<Bowler> bowlers = Database.getInstance(getApplicationContext()).bowlerDao().getBowlers();
            List<String> result = new ArrayList<>();

            for (Bowler bowler : bowlers) {
                result.add(bowler.getName());
            }

            return result;
        }
    }

    private void updateList() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bowlers);
        bowlersView.setAdapter(adapter);
    }

}
