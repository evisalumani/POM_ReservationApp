package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;

/**
 * Created by evisa on 9/21/16.
 */
public class SortByDialogFragment extends BaseDialogFragment {

    public SortByDialogFragment(View view, String title) {
        super(view, title);
    }

    protected void setupItems() {
        items = new CharSequence[] { "Nearest Distance", "Lowest Price", "Highest Rating" };
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //update filters
                filters.setPropertyToSortBy(which);

                //update ui
                updateTextInCallingActivity("Sort by: " + items[which]);
            }
        });

        //positive, set neutral
        return builder.create();
    }
}
