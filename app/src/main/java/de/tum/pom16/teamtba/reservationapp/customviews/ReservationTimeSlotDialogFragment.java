package de.tum.pom16.teamtba.reservationapp.customviews;

import android.support.v4.app.Fragment;
import android.view.View;

import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;

/**
 * Created by evisa on 10/6/16.
 */
public class ReservationTimeSlotDialogFragment extends BaseTimeSlotDialogFragment {
    private Fragment callingFragment;
    //TODO: display only available timeslots for the given date
//    private HourTimeSlot openingTimeSlot;
//    private HourTimeSlot closingTimeSlot;

    public ReservationTimeSlotDialogFragment(
            View view, HourTimeSlot timeSlot, String title, Fragment callingFragment) {
        super(view, title);
        this.timeSlot = timeSlot;
        this.callingFragment = callingFragment;
    }

    public void performClick(int which) {
        super.performClick(which);
        //update time slot
        ((RestaurantReservationFragment)callingFragment).setTimeSlotToReserve(timeSlot);
    }
}

