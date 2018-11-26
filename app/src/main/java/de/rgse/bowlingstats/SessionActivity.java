package de.rgse.bowlingstats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

import de.rgse.bowlingstats.adapters.SessionAdapter;
import de.rgse.bowlingstats.model.Session;
import de.rgse.bowlingstats.persistence.Database;
import de.rgse.bowlingstats.tasks.Callback;
import de.rgse.bowlingstats.tasks.LoadSessionsTask;

public class SessionActivity extends ToolbarActivity {

    private ListView sessionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSessions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        createFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSession();
            }
        });

        sessionView = findViewById(R.id.sessionView);
        updateList(new ArrayList<Session>());
    }

    private FloatingActionButton createFab() {
        Drawable yourDrawable = MaterialDrawableBuilder.with(getApplicationContext()) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.PLUS) // provide an icon
                .setToActionbarSize() // set the icon size
                .setColor(Color.WHITE)
                .build(); // Finally call build
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(yourDrawable);
        return fab;
    }

    private void createSession() {
        new CreateSessionTask().execute();
    }

    private void loadSessions() {
        LoadSessionsTask.loadSessions(getApplicationContext(), new Callback<List<Session>>() {
            @Override
            public void call(List<Session> sessions) {
                updateList(sessions);
            }
        });
    }

    private void updateList(List<Session> sessions) {
        SessionAdapter adapter = new SessionAdapter(this, sessions){
            @Override
            public void openSession(Session session) {
                Intent intent = new Intent(getContext(), SessionDetailActivity.class);
                intent.setData(Uri.parse(new Gson().toJson(session)));
                startActivity(intent);
            }
        };
        sessionView.setAdapter(adapter);
    }

    private final class CreateSessionTask extends  AsyncTask<Void, Void, Session> {

        @Override
        protected void onPostExecute(Session session) {
            loadSessions();
        }

        @Override
        protected Session doInBackground(Void... voids) {
            Session session = new Session();
            Database.getInstance(getApplicationContext()).sessionDao().insert(session);
            return session;
        }
    }
}
