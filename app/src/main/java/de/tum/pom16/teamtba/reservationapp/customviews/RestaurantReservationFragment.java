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
    private RelativeLayout tableResultsLayout;
    private View tableResultsView;

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
        tableResultsLayout = (RelativeLayout)v.findViewById(R.id.reservation_tableResults_layout);

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
        tableResultsView = null;
        if (availableTables == null || availableTables.size() == 0) {
            //no tables found -> show explanation in a textbox
            tableResultsView = new TextView(getActivity());
            tableResultsView.setId(R.id.tables_result_view);
            ((TextView)tableResultsView).setText("Sorry, no matching tables found. Please refine your search!");
            ((TextView)tableResultsView).setTextSize(30);
            ((TextView)tableResultsView).setGravity(Gravity.CENTER);

            tableResultsView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            //tables found -> display results in a gridview
            tableResultsView = new GridView(getActivity());
            tableResultsView.setId(R.id.tables_result_view1);
            tableResultsView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ((GridView)tableResultsView).setNumColumns(GridView.AUTO_FIT);
            ((GridView)tableResultsView).setColumnWidth(300);
            ((GridView)tableResultsView).setVerticalSpacing(10);
            ((GridView)tableResultsView).setHorizontalSpacing(10);
            ((GridView)tableResultsView).setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
            ((GridView)tableResultsView).setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            ((GridView)tableResultsView).setOnItemClickListener(getTableClickListener((GridView) tableResultsView));

            TablesGridViewAdapter adapter = new TablesGridViewAdapter(getActivity(), availableTables);
            ((GridView)tableResultsView).setAdapter(adapter);
        }

        tableResultsLayout.addView(tableResultsView);
    }

    private AdapterView.OnItemClickListener getTableClickListener(GridView tablesGridView) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table selectedTable = (Table) tablesGridView.getItemAtPosition(position);
                if (selectedTable != null) {
                    //TODO: proceed to next screen
                    //create a reservation
                    Reservation reservation = new Reservation(selectedTable, new DateTimeSlot(dateToReserve, timeSlotToReserve));
                    goToReservationDetails(reservation);
                }
            }
        };
    }

    private void goToReservationDetails(Reservation reservation) {
        Intent intent = new Intent(getActivity(), ReservationDetailsActivity.class);
        intent.putExtra("RESERVATION_DETAILS", reservation);
        startActivity(intent);
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