package de.rgse.bowlingstats.persistence;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.model.Bowler;
import de.rgse.bowlingstats.model.SeriesEntry;

public class DataImporter {

    private static final int NAME_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int SCORE_1_INDEX = 2;
    private static final int SCORE_2_INDEX = 3;
    private static final int SCORE_3_INDEX = 4;
    private static final String INVALID_SCORE = "0";

    private DataImporter() {
    }

    public static void importDataFromOurBowlingScores(View view) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                DateFormat sdf = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
                try (Reader reader = new InputStreamReader(view.getResources().openRawResource(R.raw.ourbowlingscores))) {
                    List<String[]> entries = new CSVReader(reader).readAll();

                    entries.forEach(entry -> {
                        Database db = Database.getInstance(view.getContext());

                        String bowlerName = entry[NAME_INDEX].trim();
                        Bowler bowler = db.bowlerDao().getBowlerByName(bowlerName);

                        if (bowler == null) {
                            bowler = new Bowler(bowlerName);
                            db.bowlerDao().insert(bowler);
                        }

                        try {
                            Date date = sdf.parse(entry[DATE_INDEX].trim());

                            String score1 = entry[SCORE_1_INDEX].trim();
                            String score2 = entry[SCORE_2_INDEX].trim();
                            String score3 = entry[SCORE_3_INDEX].trim();

                            if (!score1.equals(INVALID_SCORE)) {
                                db.seriesDao().insert(new SeriesEntry(bowler.getId(), Integer.valueOf(score1), date));
                            }

                            if (!score2.equals(INVALID_SCORE)) {
                                db.seriesDao().insert(new SeriesEntry(bowler.getId(), Integer.valueOf(score2), date));
                            }

                            if (!score3.equals(INVALID_SCORE)) {
                                db.seriesDao().insert(new SeriesEntry(bowler.getId(), Integer.valueOf(score3), date));
                            }

                        } catch (ParseException e) {
                            Log.e(getClass().getSimpleName(), "unable to parse data from import.csv", e);
                        }
                    });

                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(), "unable to open import.csv", e);
                }

                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(externalStorageDirectory.getAbsolutePath() + "/Android/data/com.staycalmapps.ourbowlingscores/ourbowlingscores.csv");
                System.out.println(file.exists());

                Snackbar.make(view, "Import abgeschlossen", Snackbar.LENGTH_LONG).show();
                return null;

            }
        }.execute();
    }
}
