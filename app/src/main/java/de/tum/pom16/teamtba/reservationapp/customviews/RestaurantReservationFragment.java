package de.tum.pom16.teamtba.reservationapp.customviews;

import android.content.Context;
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
    private TimeFilterCriteria timeFilterCriteria;
    private Calendar date;
    private HourTimeSlot timeSlot;

    public RestaurantReservationFragment() {
        super();
        timeFilterCriteria = (TimeFilterCriteria) GlobalSearchFilters.getSharedInstance().getFilterCriteria().get(SearchFilterType.DATE);
        date = (Calendar)timeFilterCriteria.getCriteria();
        timeSlot = timeFilterCriteria.getTimeSlot();
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
                TimeSlotDialogFragment timeDialog = new TimeSlotDialogFragment(timeTextView, "Pick time slot");
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                timeDialog.show(fragmentTransaction, "TimeSlotPicker");
            }
        };
    }

    private View.OnClickListener getDateOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialogFragment dateDialog = new DateDialogFragment(view, date);
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "DateDialog");
            }
        };
    }

    private void setupTimeForSavedFilters() {
        if (date != null) {
            dateTextView.setText(Helpers.getDateString(date));
        }

        if (timeSlot != null) {
            timeTextView.setText(timeSlot.toString());
        }

        if (date == null || timeSlot == null) {
            Toast.makeText(getActivity(), "Both date and time must be selected", Toast.LENGTH_SHORT).show();
        }
    }
}
