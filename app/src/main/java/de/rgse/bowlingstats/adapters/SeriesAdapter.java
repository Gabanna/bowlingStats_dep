package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.model.SeriesEntry;

public class SeriesAdapter extends ArrayAdapter<SeriesEntry> {

    public SeriesAdapter(Activity activity, List<SeriesEntry> data) {
        super(activity, R.layout.series_list_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bowler_item, parent, false);
            convertView.setTag(position);
        }

        SeriesEntry entry = getItem(position);
        TextView tv = convertView.findViewById(R.id.time);
        tv.setText(entry.getBowlerId());

        convertView.setTag(position);
        return convertView;
    }

}
