package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
public class TimeSlotDialogFragment extends BaseDialogFragment {
    private final static String ANY_TIME_SLOT = "Any time";

    public TimeSlotDialogFragment(View view, String title) {
        super(view, title);
    }

    protected void setupItems() {
        List<String> timeSlotStringList = Stream.of(HourTimeSlot.getAllDailyTimeSlots()).map(slot -> slot.toString()).collect(Collectors.toList());
        timeSlotStringList.add(0, TimeSlotDialogFragment.ANY_TIME_SLOT);
        items = timeSlotStringList.toArray(new String[timeSlotStringList.size()]);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //update filters
                String timeSlotStr = (String) items[which];
                filters.setTimeSlot(HourTimeSlot.fromString(timeSlotStr));

                //update ui
                updateTextInCallingActivity(String.valueOf(items[which]));
            }
        });

        return builder.create();
    }
}
