package de.rgse.bowlingstats;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.persistence.Database;

public class CreateBowler extends AppCompatActivity {

    public static final int CREATE_BOWLER_REQUEST_CODE = 5000;
    public static final int EDIT_BOWLER_REQUEST_CODE = 5001;

    private Button createBtn;
    private Button cancleBtn;
    private EditText editNameOfBowler;
    private Bowler bowler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bowler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editNameOfBowler = (EditText) findViewById(R.id.editNameOfBowler);
        createBtn = (Button) findViewById(R.id.create_btn);

        cancleBtn = (Button) findViewById(R.id.cancle_btn);
        cancleBtn.setOnClickListener(cancle());

        Intent intent = getIntent();
        if(null != intent.getData()) {
            bowler = new Gson().fromJson(intent.getData().toString(), Bowler.class);
            editNameOfBowler.setText(bowler.getName());
            createBtn.setOnClickListener(updateBowler());
        } else {
            createBtn.setOnClickListener(createBowler());
        }

    }

    private View.OnClickListener cancle() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        };
    }

    private View.OnClickListener updateBowler() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editNameOfBowler.getText().toString().trim();
                if (name.length() > 0) {
                    editNameOfBowler.setError(null);
                    new UpdateBowlerTask().execute(name);
                } else {
                    editNameOfBowler.setError(getResources().getString(R.string.name_is_mandatory));
                }
            }
        };
    }

    private View.OnClickListener createBowler() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editNameOfBowler.getText().toString().trim();
                if (name.length() > 0) {
                    editNameOfBowler.setError(null);
                    new CreateBowlerTask().execute(name);
                } else {
                    editNameOfBowler.setError(getResources().getString(R.string.name_is_mandatory));
                }
            }
        };
    }

    private class CreateBowlerTask extends AsyncTask<String, Void, Object> {

        @Override
        protected void onPostExecute(Object result) {
            Intent data = new Intent();

            if(result instanceof Bowler) {
                data.setData(Uri.parse(new Gson().toJson(result)));
                setResult(RESULT_OK, data);
                finish();

            } else {
                editNameOfBowler.setError(getResources().getString(R.string.name_is_taken));
            }
        }

        @Override
        protected Object doInBackground(String... names) {
            try {
                Bowler bowler = new Bowler(names[0]);
                Database.getInstance(getApplicationContext()).bowlerDao().insert(bowler);
                return bowler;

            } catch(SQLiteConstraintException e) {
                return e;
            }
        }
    }

    private class UpdateBowlerTask extends AsyncTask<String, Void, Object> {

        @Override
        protected void onPostExecute(Object result) {
            Intent data = new Intent();

            if(result instanceof Bowler) {
                data.setData(Uri.parse(new Gson().toJson(result)));
                setResult(RESULT_OK, data);
                finish();

            } else {
                editNameOfBowler.setError(getResources().getString(R.string.name_is_taken));
            }
        }

        @Override
        protected Object doInBackground(String... names) {
            try {
                bowler.setName(names[0]);
                Database.getInstance(getApplicationContext()).bowlerDao().update(bowler);
                return bowler;

            } catch(SQLiteConstraintException e) {
                return e;
            }
        }
    }
}
