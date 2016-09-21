package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.models.Constants;

/**
 * Created by evisa on 9/21/16.
 */
public class PriceDialogFragment extends DialogFragment {
    //model
    private GlobalSearchFilters filters;
    private String title;
    private CharSequence[] items;
    private boolean[] checkedItems;

    public PriceDialogFragment() {
    }

    public PriceDialogFragment(String title) {
        filters = GlobalSearchFilters.getSharedInstance();
        this.title = title;
        setupItems();
    }

    private void setupItems() {
        items = new CharSequence[] {"Any Price", "€", "€€", "€€€", "€€€€"};
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

                //positive, set neutral
        return builder.create();
    }
}
