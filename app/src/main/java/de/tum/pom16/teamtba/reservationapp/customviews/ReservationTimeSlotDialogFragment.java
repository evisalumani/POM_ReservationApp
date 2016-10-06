package de.tum.pom16.teamtba.reservationapp.customviews;

import android.view.View;

import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;

/**
 * Created by evisa on 10/6/16.
 */
public class ReservationTimeSlotDialogFragment extends BaseTimeSlotDialogFragment {
    public ReservationTimeSlotDialogFragment(View view, HourTimeSlot timeSlot, String title) {
        super(view, title);
        this.timeSlot = timeSlot;
    }

    public void performClick(int which) {
        super.performClick(which);
        //update global filters
        //TODO
        //filters.setTimeSlot(HourTimeSlot.fromString((String) items[which]));
    }
}

