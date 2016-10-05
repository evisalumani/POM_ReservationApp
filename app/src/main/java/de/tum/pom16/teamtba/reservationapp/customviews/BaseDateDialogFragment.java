package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;

/**
 * Created by evisa on 10/6/16.
 */
public class BaseDateDialogFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener {
    protected Calendar selectedDate;

    protected BaseDateDialogFragment(View view, Calendar date) {
        super(view, null);
        selectedDate = date;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        if (viewInCallingActivity != null && selectedDate != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); //disable past dates
            return datePickerDialog;
        }

        return null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        selectedDate.set(year, monthOfYear, dayOfMonth);
        updateTextInCallingActivity(Helpers.getDateString(selectedDate));
        //TODO: override to set filters
    }
}