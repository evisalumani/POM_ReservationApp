package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;

/**
 * Created by evisa on 9/13/16.
 */
public class CuisineDialogFragment extends DialogFragment {
    public final static String ANY_CUISINE = "All";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        CharSequence[] timeSlotsArray = null;

        builder.setTitle("Cuisine");
        //R.array.restaurant_types
        Set<String> cuisineSet = Stream.of(GlobalSearchFilters.getSharedInstance().getCuisines().keySet()).map(key -> key.toString()).collect(Collectors.toSet());
        String[] cuisineArray = new String[] {};
        cuisineSet.toArray(cuisineArray);
        List<Boolean> b = Stream.of(GlobalSearchFilters.getSharedInstance().getCuisines().values()).map(b -> b.booleanValue()).collect(Collectors.toList());
        List<Boolean> booleanValues = (List<Boolean>)GlobalSearchFilters.getSharedInstance().getCuisines().values();
        boolean[] booleanArray = new boolean[] {};
        booleanValues.toArray(booleanArray);
        //Helpers.toPrimitiveBooleanArray()
        builder.setMultiChoiceItems(cuisineArray, new boolean[] {}, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });

//        builder.setItems(timeSlotsArray, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), timeSlotsArray[which], Toast.LENGTH_SHORT).show();
//
//            }
//        });

        return builder.create();
    }
}
