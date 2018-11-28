package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.rgse.bowlingstats.R;

public class SeriesListAdapter extends ArrayAdapter<Date> {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();

    public SeriesListAdapter(Activity activity, List<Date> data) {
        super(activity, R.layout.series_list_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.series_item, parent, false);
            convertView.setTag(position);
        }

        Date dateTime = getItem(position);
        TextView tv = convertView.findViewById(R.id.time);
        tv.setText(DATE_FORMAT.format(dateTime));

        convertView.setTag(position);
        return convertView;
    }

}
