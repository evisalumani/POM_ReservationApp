package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

/**
 * Created by evisa on 9/13/16.
 */
public class CuisineDialogFragment extends DialogFragment {
    //model
    private GlobalSearchFilters filters;
    private String title;
    private CharSequence[] items;
    private boolean[] checkedItems;

    public CuisineDialogFragment() {
    }

    public CuisineDialogFragment(String title) {
        filters = GlobalSearchFilters.getSharedInstance();
        this.title = title;
        setupItems();
    }

    private void setupItems() {
        Set<String> cuisineSet = Stream.of(filters.getCuisines().keySet()).map(key -> key.toString()).collect(Collectors.toSet());
        items = cuisineSet.toArray(new String[cuisineSet.size()]);

        List<Boolean> booleanList = new ArrayList<Boolean>(filters.getCuisines().values());
        checkedItems = fromListToPrimitiveBooleanArray(booleanList);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (title != null && items != null && checkedItems != null) {
            builder.setTitle(title);
            builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    //TODO: apply logic, or some state machine, for the different cases of interaction
                    //e.g. All is by default selected, if a specific type is selected, All will be deselected etc.

                    checkedItems[which] = isChecked;
                }
            });

            builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setSelectedCuisines();
                }
            });

            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getDialog().dismiss(); //dismiss dialog
                }
            });


        }

        return builder.create();
    }

    private void setSelectedCuisines() {
        //set up filters' cuisines according to checkedItems boolean array
        for (int i=0; i<checkedItems.length; i++) {
            String cuisineStr = String.valueOf(items[i]);
            CuisineType cuisineType = CuisineType.valueOf(cuisineStr);
            filters.setCuisineChoice(cuisineType, checkedItems[i]);
        }
    }

    private boolean[] fromListToPrimitiveBooleanArray(List<Boolean> booleanList)  {
        if (booleanList != null && booleanList.size() > 0) {
            boolean[] booleanArray = new boolean[booleanList.size()];

            for (int i=0; i<booleanArray.length; i++) {
                booleanArray[i] = booleanList.get(i);
            }

            return booleanArray;
        }

        return null;
    }
}
