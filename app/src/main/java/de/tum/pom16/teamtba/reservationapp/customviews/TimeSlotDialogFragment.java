package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.dataaccess.DataGenerator;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 9/8/16.
 */
public class TimeSlotDialogFragment extends DialogFragment {

    public final static String ANY_TIME_SLOT = "Any time";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        List<String> timeSlotStringList = Stream.of(HourTimeSlot.getAllDailyTimeSlots()).map(slot -> slot.toString()).collect(Collectors.toList());
        timeSlotStringList.add(0, TimeSlotDialogFragment.ANY_TIME_SLOT);

        CharSequence[] timeSlotsArray = timeSlotStringList.toArray(new String[timeSlotStringList.size()]);

        builder.setTitle("Pick time slot");

        builder.setItems(timeSlotsArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), timeSlotsArray[which], Toast.LENGTH_SHORT).show();

            }
        });

        return builder.create();
    }
}
