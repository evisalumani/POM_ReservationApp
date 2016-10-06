package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;

/**
 * Created by evisa on 10/6/16.
 */
public class BaseTimeSlotDialogFragment extends BaseDialogFragment {
    private final static String ANY_TIME_SLOT = "Any time";
    protected HourTimeSlot timeSlot;

    protected BaseTimeSlotDialogFragment(View view, String title) {
        super(view, title);
    }

    protected void setupItems() {
        List<String> timeSlotStringList = Stream.of(HourTimeSlot.getAllDailyTimeSlots()).map(slot -> slot.toString()).collect(Collectors.toList());
        timeSlotStringList.add(0, BaseTimeSlotDialogFragment.ANY_TIME_SLOT);
        items = timeSlotStringList.toArray(new String[timeSlotStringList.size()]);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(items, getDialogClickListener());
        return builder.create();
    }

    protected DialogInterface.OnClickListener getDialogClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performClick(which);
            }
        };
    }

    protected void performClick(int which) {
        //update selected timeslot
        timeSlot = HourTimeSlot.fromString((String) items[which]);
        //update ui
        updateTextInCallingActivity(String.valueOf(items[which]));
        //TODO: override to update filters
    }
}