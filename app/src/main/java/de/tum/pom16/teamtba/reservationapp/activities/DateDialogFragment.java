package de.tum.pom16.teamtba.reservationapp.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilter;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;

/**
 * Created by evisa on 9/7/16.
 */
public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TextView selectedDateTextView;
    private Calendar selectedDate;

    public DateDialogFragment() {}

    public DateDialogFragment(View view, Calendar date) {
        selectedDateTextView = (TextView)view;
        selectedDate = date;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        if (selectedDateTextView != null && selectedDate != null) {
            return new DatePickerDialog(getActivity(), this, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        }

        return null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        selectedDate.set(year, monthOfYear, dayOfMonth);
        GlobalSearchFilters.getSharedInstance().setDate(selectedDate);

        setDateText();
    }

    public void setDateText() {
        selectedDateTextView.setText(GlobalSearchFilters.getSharedInstance().getDateString());
    }
}
