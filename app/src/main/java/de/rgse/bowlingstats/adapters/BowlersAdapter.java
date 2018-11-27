package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.model.Bowler;

public class BowlersAdapter extends ArrayAdapter<Bowler> {

    public BowlersAdapter(Activity activity, List<Bowler> data) {
        super(activity, R.layout.bowler_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bowler_item, parent, false);
            convertView.setTag(position);
        }

        Bowler bowler = getItem(position);
        TextView tv = convertView.findViewById(R.id.name);
        tv.setText(bowler.getName());

        convertView.setTag(position);
        return convertView;
    }

}
