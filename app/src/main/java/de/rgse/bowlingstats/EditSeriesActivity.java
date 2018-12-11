package de.rgse.bowlingstats;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Locale;

import de.rgse.bowlingstats.adapters.EditableSeriesEntryAdapter;
import de.rgse.bowlingstats.filter.InputFilterMinMax;
import de.rgse.bowlingstats.model.BowlerSeriesEntries;
import de.rgse.bowlingstats.model.SeriesEntry;
import de.rgse.bowlingstats.tasks.UpdateSeriesEntryTask;

public class EditSeriesActivity extends AppCompatActivity {

    private static final String TITLE_TEMPLATE = "%s @ %s";
    private BowlerSeriesEntries bowlerSeriesEntries;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_series);

        listView = findViewById(R.id.editList);
        listView.setOnItemClickListener((parent, view, position, id) -> openEditDialog((SeriesEntry) listView.getAdapter().getItem(position)));

        String dataString = getIntent().getDataString();

        if (dataString != null) {
            bowlerSeriesEntries = new Gson().fromJson(dataString, BowlerSeriesEntries.class);
            String date = DateFormat.getDateInstance().format(bowlerSeriesEntries.getEntries().get(0).getDateTime());
            setTitle(String.format(TITLE_TEMPLATE, bowlerSeriesEntries.getBowler().getName(), date));

            updateList();
        }
    }

    private void openEditDialog(SeriesEntry entry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setText(String.format(Locale.getDefault(), "%d", entry.getValue()));
        input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[]{new InputFilterMinMax(0, 300)});
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String value = input.getText().toString();
            entry.setValue(Integer.valueOf(value));
            UpdateSeriesEntryTask.updateSeriesEntry(entry, EditSeriesActivity.this, (voids) -> {
                updateList();
                dialog.dismiss();
            });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateList() {
        EditableSeriesEntryAdapter adapter = new EditableSeriesEntryAdapter(this, bowlerSeriesEntries.getEntries());
        listView.setAdapter(adapter);
    }
}
