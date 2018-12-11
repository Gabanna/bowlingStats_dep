package de.rgse.bowlingstats.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import de.rgse.bowlingstats.MenuItemHandler;
import de.rgse.bowlingstats.R;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    private MenuItemHandler menuItemHandler;

    public MenuItemAdapter(Activity activity, List<MenuItem> data) {
        super(activity, R.layout.menu_item, data);
        menuItemHandler = new MenuItemHandler(activity);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.menu_item, parent, false);
            convertView.setTag(position);
        }

        MenuItem item = getItem(position);
        if (item != null) {
            Button mb = convertView.findViewById(R.id.material_button);
            mb.setText(item.getTitle());
            mb.setCompoundDrawables(item.getIcon(), null, null, null);
            mb.setOnClickListener(v -> menuItemHandler.handleItemSelect(item));
        }

        convertView.setTag(position);
        return convertView;
    }


}
