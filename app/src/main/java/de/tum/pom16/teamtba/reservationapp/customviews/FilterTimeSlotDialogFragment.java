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
 * Created by evisa on 9/8/16.
 */
public class FilterTimeSlotDialogFragment extends BaseTimeSlotDialogFragment {
    public FilterTimeSlotDialogFragment(View view, String title) {
        super(view, title);
    }

    public void performClick(int which) {
        super.performClick(which);
        //update global filters
        filters.setTimeSlot(timeSlot);
    }
}
