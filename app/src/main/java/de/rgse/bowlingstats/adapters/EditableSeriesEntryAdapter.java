package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.model.SeriesEntry;

public class EditableSeriesEntryAdapter extends ArrayAdapter<SeriesEntry> {

    public EditableSeriesEntryAdapter(Activity activity, List<SeriesEntry> data) {
        super(activity, R.layout.editable_series_item, data);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.editable_series_item, parent, false);
            convertView.setTag(position);
        }

        SeriesEntry entry = getItem(position);

        if(entry != null) {
            TextView tv = convertView.findViewById(R.id.editEntry);
            tv.setText(String.format(Locale.getDefault(), "%d", entry.getValue()));
        }

        convertView.setTag(position);
        return convertView;
    }

}
