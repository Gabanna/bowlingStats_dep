package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import de.rgse.bowlingstats.model.Bowler;

public abstract class BowlersAdapter extends ArrayAdapter<Bowler> implements View.OnClickListener {

    private Activity activity;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView txtName;
        ImageView info;

    }

    public BowlersAdapter(Activity activity, List<Bowler> data) {
        super(activity, R.layout.bowler_item, data);
        this.activity = activity;
    }

    public abstract void onDelete(Bowler bowler);

    @Override
    public void onClick(final View v) {
        final Bowler bowler = getItem((int) v.getTag());

        if (bowler != null) {
            new AlertDialog.Builder(activity)
                    .setTitle(v.getResources().getString(R.string.deleteBowler))
                    .setMessage(String.format(v.getResources().getString(R.string.doDeleteBowler), bowler.getName()))
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            onDelete(bowler);
                        }
                    })
                    .setNegativeButton(R.string.no, null).show();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bowler dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

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

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        return convertView;
    }

}
