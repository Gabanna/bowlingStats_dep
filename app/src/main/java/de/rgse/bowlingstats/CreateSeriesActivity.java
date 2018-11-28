package de.rgse.bowlingstats;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import de.rgse.bowlingstats.filter.InputFilterMinMax;
import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.tasks.CreateSeriesEntryTask;
import de.rgse.bowlingstats.tasks.LoadBowlerByNameTask;
import de.rgse.bowlingstats.tasks.LoadBowlersTask;

public class CreateSeriesActivity extends AppCompatActivity {

    public static final int CREATE_SERIES_REQUEST_CODE = 5002;
    private static final DateFormat dateFormatter = DateFormat.getDateInstance();

    private EditText fromDateEtxt;
    private Spinner spinner;
    private EditText score;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadBowlers();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_series);

        fromDateEtxt = findViewById(R.id.date);

        initSpinner();
        initDateEdit();
        initButtons();

        score = findViewById(R.id.score);
        score.setFilters(new InputFilter[]{new InputFilterMinMax(0, 300)});
    }

    private void initSpinner() {
        spinner = findViewById(R.id.bowlers_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = (String) spinner.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                name = null;
            }
        });
    }

    private void initButtons() {
        Button cancleBtn = findViewById(R.id.cancle_btn);
        cancleBtn.setOnClickListener(v -> finish());

        Button createBtn = findViewById(R.id.create_btn);
        createBtn.setOnClickListener(v -> loadBowlerByName());
    }

    private void loadBowlerByName() {
        LoadBowlerByNameTask.loadBowlerByName(name, getApplicationContext(), bowler -> {
            try {
                int scoreToEnter = Integer.parseInt(score.getText().toString());
                Date date = dateFormatter.parse(fromDateEtxt.getText().toString());
                SeriesEntry entry = new SeriesEntry(bowler.getId(), scoreToEnter, date);
                CreateSeriesEntryTask.createSeriesEntry(entry, getApplicationContext(), voids -> {
                    Intent data = new Intent();
                    data.setData(Uri.parse(fromDateEtxt.getText().toString()));
                    setResult(Activity.RESULT_OK, data);
                    finish();
                });
            } catch (ParseException e) {
                Log.e(CreateSeriesActivity.class.getSimpleName(), "unable to parse input of fromDateEtxt", e);
            }
        });
    }

    private void initDateEdit() {
        Calendar newCalendar = Calendar.getInstance();
        String dataString = getIntent().getDataString();

        if (dataString != null) {
            try {
                Date date = DateFormat.getDateInstance().parse(dataString);
                newCalendar.setTime(date);
            } catch (ParseException e) {
                Log.e(CreateSeriesActivity.class.getSimpleName(), "unable to parse data from intent", e);
            }
        }

        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        fromDateEtxt.setText(dateFormatter.format(newCalendar.getTime()));

        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDateEtxt.setOnClickListener(v -> fromDatePickerDialog.show());
    }

    private void updateSpinner(List<String> bowlerNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bowlerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void loadBowlers() {
        LoadBowlersTask.loadBowlers(this, bowlers -> {
            List<String> bowlerNames = bowlers.stream().map(Bowler::getName).collect(Collectors.toList());
            updateSpinner(bowlerNames);
        });
    }
}
