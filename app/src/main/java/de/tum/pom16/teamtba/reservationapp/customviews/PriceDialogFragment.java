package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.models.Constants;

/**
 * Created by evisa on 9/21/16.
 */
public class PriceDialogFragment extends BaseDialogFragment {
    public PriceDialogFragment(View view, String title) {
        super(view, title);
    }

    protected void setupItems() {
        items = new CharSequence[] {"Any Price", "€", "€€", "€€€", "€€€€"};
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //update filters

                //update UI
                updateTextInCallingActivity("Max Price: " + items[which]);
            }
        });

        return builder.create();
    }
}
