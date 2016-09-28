package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by evisa on 9/7/16.
 */
public class DateDialogFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener {
    private Calendar selectedDate;

    public DateDialogFragment(View view, Calendar date) {
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
        filters.setDate(selectedDate);

        updateTextInCallingActivity(filters.getDateString());
    }
}
