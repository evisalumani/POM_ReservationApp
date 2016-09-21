package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

/**
 * Created by evisa on 9/13/16.
 */
public class CuisineDialogFragment extends BaseDialogFragment {
    //model
    private boolean[] checkedItems;
    private int nrOfSelectedCuisines;

    public CuisineDialogFragment(View view, String title) {
        super(view, title);
    }

    protected void setupItems() {
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
                    //apply logic for the different cases of interaction
                    //TODO: use a state or observer pattern

                    if (which == 0 && isChecked) {
                        //ALL (0th position) is selected ->
                        //set ALL to true, and the rest to false (since it encompasses all)
                        Arrays.fill(checkedItems, false);
                        checkedItems[0] = true;

                        //notify UI
                        deselectAllCuisinesInDialog(dialog);
                    } else if (which != 0 && isChecked && checkedItems[0]) {
                        //if a specific cuisine is chosen, and ALL was set to true ->
                        //set ALL to false
                        checkedItems[which] = isChecked;
                        checkedItems[0] = false;

                        setCuisineInDialog(dialog, 0, false);
                        //(which, isChecked) is modified by the default behavior of the checkbox, no need to set it up manually like for (0, false)
                    } else {
                        checkedItems[which] = isChecked;
                    }

                    //if everything is deselected until nothing remains -> ALL is selected
                    if (areAllOptionsDeselected(dialog)) {
                        checkedItems[0] = true;
                        setCuisineInDialog(dialog, 0, true);
                    }

                    //if all specific cuisines are selected -> they become deselected and ALL selected
                    if (areAllSpecificCuisinesSelected(dialog)) {
                        //select "All"
                        Arrays.fill(checkedItems, false);
                        checkedItems[0] = true;
                        deselectAllCuisinesInDialog(dialog);
                    }
                }
            });

            builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setSelectedCuisines();
                    updateTextInCallingActivity("Cuisines: (" + (checkedItems[0] ? "All" :nrOfSelectedCuisines) + ")");
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
        nrOfSelectedCuisines = 0;

        for (int i=0; i<checkedItems.length; i++) {
            String cuisineStr = String.valueOf(items[i]);
            CuisineType cuisineType = CuisineType.valueOf(cuisineStr);
            filters.setCuisineChoice(cuisineType, checkedItems[i]);

            if (checkedItems[i]) nrOfSelectedCuisines++;
        }
    }

    private void deselectAllCuisinesInDialog(DialogInterface dialog) {
        ListView listView = ((AlertDialog)dialog).getListView();
        for (int i=0; i<listView.getCount(); i++) {
            listView.setItemChecked(i, i == 0? true : false); //ALL remains selected
        }
    }

    private boolean areAllOptionsDeselected(DialogInterface dialog) {
        ListView listView = ((AlertDialog)dialog).getListView();
        for (int i=0; i<listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                return false; //something is selected
            }
        }

        return true;
    }

    private boolean areAllSpecificCuisinesSelected(DialogInterface dialog) {
        ListView listView = ((AlertDialog)dialog).getListView();
        //position 0 is for "All" option => start iterating at 1
        for (int i=1; i<listView.getCount(); i++) {
            if (!listView.isItemChecked(i)) {
                return false; //there is a not selected cuisine
            }
        }

        return true;
    }

    private void setCuisineInDialog(DialogInterface dialog, int position, boolean isChecked) {
        ListView listView = ((AlertDialog)dialog).getListView();
        listView.setItemChecked(position, isChecked);
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
