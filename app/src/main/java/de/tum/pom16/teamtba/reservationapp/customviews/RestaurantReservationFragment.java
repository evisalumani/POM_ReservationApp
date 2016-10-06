package de.tum.pom16.teamtba.reservationapp.customviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.dataaccess.SearchFilterType;
import de.tum.pom16.teamtba.reservationapp.dataaccess.TimeFilterCriteria;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;

/**
 * Created by evisa on 9/22/16.
 */
public class RestaurantReservationFragment extends PlaceholderFragment {
    private TextView dateTextView;
    private TextView timeTextView;
    private Button findTableButton;
    private GridView gridView;

    private TablesGridViewAdapter adapter;

    //criteria to filter tables
    private Calendar dateToReserve;
    private HourTimeSlot timeSlotToReserve;

    public RestaurantReservationFragment() {
        super();
        TimeFilterCriteria timeFilterCriteria = (TimeFilterCriteria) GlobalSearchFilters.getSharedInstance().getFilterCriteria().get(SearchFilterType.DATE);
        dateToReserve = (Calendar)timeFilterCriteria.getCriteria();
        timeSlotToReserve = timeFilterCriteria.getTimeSlot();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_reservations, container, false);
        dateTextView = (TextView)v.findViewById(R.id.reservation_calendar_textview);
        timeTextView = (TextView)v.findViewById(R.id.reservation_time_textview);
        findTableButton = (Button)v.findViewById(R.id.reservation_find_table_button);

        setupTimeForSavedFilters();

        dateTextView.setOnClickListener(getDateOnClickListener());
        timeTextView.setOnClickListener(getTimeOnClickListener());
        gridView = (GridView) v.findViewById(R.id.reservation_tables_gridview);

//        adapter = new TablesGridViewAdapter(getActivity(), restaurant.getTables());
//        gridView.setAdapter(adapter);
        return v;
    }

    private View.OnClickListener getTimeOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationTimeSlotDialogFragment timeDialog = new ReservationTimeSlotDialogFragment(timeTextView, timeSlotToReserve, "Pick time slot");
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                timeDialog.show(fragmentTransaction, "TimeSlotPicker");
            }
        };
    }

    private View.OnClickListener getDateOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationDateDialogFragment dateDialog = new ReservationDateDialogFragment(view, dateToReserve);
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "DateDialog");
            }
        };
    }

    private void setupTimeForSavedFilters() {
        if (dateToReserve != null) {
            dateTextView.setText(Helpers.getDateString(dateToReserve));
        }

        if (timeSlotToReserve != null) {
            timeTextView.setText(timeSlotToReserve.toString());
        }

        if (dateToReserve == null || timeSlotToReserve == null) {
            Toast.makeText(getActivity(), "Both dateToReserve and time must be selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        //TODO: this is not called when closing the timepicker dialog
        super.onResume();
        Toast.makeText(getActivity(), (timeSlotToReserve == null ? "NULL" : timeSlotToReserve.toString()), Toast.LENGTH_SHORT).show();
    }
}
