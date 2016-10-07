package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by evisa on 10/6/16.
 */
public class ReservationDateDialogFragment extends BaseDateDialogFragment implements DatePickerDialog.OnDateSetListener {
    private Fragment callingFragment;
    public ReservationDateDialogFragment(View view, Calendar date, Fragment callingFragment) {
        super(view, date);
        this.callingFragment = callingFragment;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        super.onDateSet(view, year, monthOfYear, dayOfMonth);
        //update date
        ((RestaurantReservationFragment)callingFragment).setDateToReserve(selectedDate);
    }
}