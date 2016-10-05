package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;

/**
 * Created by evisa on 9/7/16.
 */
public class FilterDateDialogFragment extends BaseDateDialogFragment implements DatePickerDialog.OnDateSetListener {
    public FilterDateDialogFragment(View view, Calendar date) {
        super(view, date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        super.onDateSet(view, year, monthOfYear, dayOfMonth);
        //update global filters
        filters.setDate(selectedDate);
    }
}
