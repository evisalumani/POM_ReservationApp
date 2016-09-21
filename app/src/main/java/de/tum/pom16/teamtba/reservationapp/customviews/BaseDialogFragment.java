package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;

/**
 * Created by evisa on 9/21/16.
 */
public class BaseDialogFragment extends DialogFragment {
    //model
    protected GlobalSearchFilters filters;
    protected String title;
    protected CharSequence[] items;

    //UI
    protected TextView viewInCallingActivity; //e.g. text-view, from which click the dialog originated

    protected BaseDialogFragment() { }

    protected BaseDialogFragment(View view, String title) {
        filters = GlobalSearchFilters.getSharedInstance();
        viewInCallingActivity = (TextView)view;
        this.title = title;
        setupItems();
    }

    protected void setupItems() { }

    protected void updateTextInCallingActivity (String text) {
        viewInCallingActivity.setText(text);
    }

    protected void updateFilters() { } //not sure
}
