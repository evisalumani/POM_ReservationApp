package de.tum.pom16.teamtba.reservationapp.customviews;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.activities.ReservationDetailsActivity;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.dataaccess.SearchFilterType;
import de.tum.pom16.teamtba.reservationapp.dataaccess.TimeFilterCriteria;
import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.DateTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.Reservation;
import de.tum.pom16.teamtba.reservationapp.models.Table;
import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;

/**
 * Created by evisa on 9/22/16.
 */
public class RestaurantReservationFragment extends PlaceholderFragment {
    private TextView dateTextView;
    private TextView timeTextView;
    private Button findTableButton;

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
        setFindTableButtonEnabled();

        setupTimeForSavedFilters();

        dateTextView.setOnClickListener(getDateOnClickListener());
        timeTextView.setOnClickListener(getTimeOnClickListener());
        findTableButton.setOnClickListener(getFindTableClickListener());

        return v;
    }

    private View.OnClickListener getFindTableClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateToReserve != null && timeSlotToReserve != null) {
                    //perform table search
                    List<Table> availableTables = restaurant.getAvailableTables(dateToReserve, timeSlotToReserve);
                    displayTableResults(availableTables);
                } else {
                    Toast.makeText(getActivity(), "Select both date and time", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void displayTableResults(List<Table> availableTables) {
        //use replace for fragments, rather than add, to avoid fragments being placed on top of each other
        if (availableTables == null || availableTables.size() == 0) {
            NoTablesResultsFragment noTablesFragment = new NoTablesResultsFragment();
            //TODO: check if container is not null
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.reservation_tableResults_layout, noTablesFragment).commit();
        } else {
            TablesGridViewAdapter adapter = new TablesGridViewAdapter(getActivity(), availableTables);
            TablesGridViewFragment tablesGridFragment = new TablesGridViewFragment(adapter, dateToReserve, timeSlotToReserve);
            //TODO: check if container is not null
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.reservation_tableResults_layout, tablesGridFragment).commit();
        }
    }

    private View.OnClickListener getTimeOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationTimeSlotDialogFragment timeDialog = new ReservationTimeSlotDialogFragment(timeTextView, timeSlotToReserve, "Pick time slot", RestaurantReservationFragment.this);
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                timeDialog.show(fragmentTransaction, "TimeSlotPicker");
            }
        };
    }

    private View.OnClickListener getDateOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationDateDialogFragment dateDialog = new ReservationDateDialogFragment(view, dateToReserve, RestaurantReservationFragment.this);
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
            Toast.makeText(getActivity(), "Select both date and time", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTimeSlotToReserve(HourTimeSlot timeSlotToReserve) {
        this.timeSlotToReserve = timeSlotToReserve;
        setFindTableButtonEnabled();

    }

    public void setDateToReserve(Calendar dateToReserve) {
        this.dateToReserve = dateToReserve;
        setFindTableButtonEnabled();
    }

    private void setFindTableButtonEnabled() {
        findTableButton.setEnabled(dateToReserve != null && timeSlotToReserve != null);
    }
}