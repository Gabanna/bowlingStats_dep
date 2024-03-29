package de.rgse.bowlingstats.factories;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import de.rgse.bowlingstats.R;

public class FabFactory {

    public static FloatingActionButton createFab(Activity activity) {
        return FabFactory.createFab(activity, MaterialDrawableBuilder.IconValue.ACCOUNT_PLUS);
    }

    public static FloatingActionButton createFab(Activity activity, MaterialDrawableBuilder.IconValue iconValue) {
        Drawable yourDrawable = MaterialDrawableBuilder.with(activity.getApplicationContext()) // provide a context
                .setIcon(iconValue) // provide an icon
                .setToActionbarSize() // set the icon size
                .setColor(Color.WHITE)
                .build(); // Finally call build
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setImageDrawable(yourDrawable);
        return fab;
    }
}
