package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.List;

import de.rgse.bowlingstats.R;
import de.rgse.bowlingstats.SessionDetailActivity;
import de.rgse.bowlingstats.model.Session;

public abstract class SessionAdapter extends ArrayAdapter<Session> implements View.OnClickListener {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance();
    private Activity activity;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView txtName;
        ImageView info;

    }

    public SessionAdapter(Activity activity, List<Session> data) {
        super(activity, R.layout.session_item, data);
        this.activity = activity;
    }

    @Override
    public void onClick(final View v) {
        final Session session = getItem((int) v.getTag());
        if (session != null) {
            openSession(session);
        }
    }

    public abstract void openSession(Session session);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Session dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bowler_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.info = convertView.findViewById(R.id.item_info);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(DATE_FORMAT.format(dataModel.getDateTime()));
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        return convertView;
    }

}
