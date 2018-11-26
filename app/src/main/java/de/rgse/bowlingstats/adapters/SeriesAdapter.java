package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.model.Series;

public class SeriesAdapter extends ArrayAdapter<Series> implements  View.OnClickListener{

    private int lastPosition = -1;
    private Activity activity;


    private static class ViewHolder {
        TextView txtName;
        ImageView info;
    }

    public SeriesAdapter(Activity activity, List<Series> data) {
        super(activity, R.layout.series_item, data);
        this.activity = activity;
    }

    @Override
    public void onClick(final View v) {
        final Series series = getItem((int) v.getTag());
        if (series != null) {
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Series dataModel = getItem(position);
        SeriesAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new SeriesAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bowler_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.info = convertView.findViewById(R.id.item_info);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SeriesAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getBowlerId());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        return convertView;
    }
}
