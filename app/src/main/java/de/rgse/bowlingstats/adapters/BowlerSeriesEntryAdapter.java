package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.model.BowlerSeriesEntries;
import de.rgse.bowlingstats.model.SeriesEntry;

public class BowlerSeriesEntryAdapter extends ArrayAdapter<BowlerSeriesEntries> {

    private static final int MARGIN = 12;
    private static final int PADDING = 5;
    private static final int TEXT_SIZE = 14;

    public BowlerSeriesEntryAdapter(Activity activity, List<BowlerSeriesEntries> data) {
        super(activity, R.layout.entry_item, data);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.entry_item, parent, false);
            convertView.setTag(position);
        }

        BowlerSeriesEntries bse = getItem(position);

        if(bse != null) {
            TextView tv = convertView.findViewById(R.id.name);
            tv.setText(bse.getBowler().getName());

            LinearLayout ll = convertView.findViewById(R.id.scores);
            ll.removeAllViews();

            for(int i = 0; i < bse.getEntries().size(); i++) {
                SeriesEntry e = bse.getEntries().get(i);

                TextView view = new TextView(getContext());
                view.setPadding(PADDING, PADDING, PADDING, PADDING);

                if(i % 2 == 0) {
                    view.setBackgroundColor(Color.LTGRAY);
                }

                view.setText(String.format(Locale.getDefault(), "%d", e.getValue()));

                ViewGroup.MarginLayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(MARGIN, 0, MARGIN, 0);
                view.setLayoutParams(params);
                view.setTextSize(TEXT_SIZE);
                view.setWidth((TEXT_SIZE * 4) + (2 * MARGIN) + (2 * PADDING));
                view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                ll.addView(view);
            }
        }

        convertView.setTag(position);
        return convertView;
    }

}
