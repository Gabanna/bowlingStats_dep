package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.tasks.LoadBowlersCountForDateTask;

public class SeriesListAdapter extends ArrayAdapter<Date> {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();

    public SeriesListAdapter(Activity activity, List<Date> data) {
        super(activity, R.layout.series_list_item, data);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.series_item, parent, false);
            convertView.setTag(position);
        }

        final Date dateTime = getItem(position);

        if (dateTime != null) {
            final TextView tv = convertView.findViewById(R.id.time);
            final View v = convertView;

            tv.setText(DATE_FORMAT.format(dateTime));
            LoadBowlersCountForDateTask.loadBowlersCountForDate(dateTime, getContext(), count -> {
                TextView badge = v.findViewById(R.id.badge_notification);
                if (badge != null) {
                    badge.setText(String.format(Locale.getDefault(), "%d", count));
                }
            });
        }

        convertView.setTag(position);
        return convertView;
    }

}
