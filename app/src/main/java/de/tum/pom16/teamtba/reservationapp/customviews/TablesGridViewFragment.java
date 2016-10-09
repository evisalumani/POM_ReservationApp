package de.tum.pom16.teamtba.reservationapp.customviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.activities.ReservationDetailsActivity;
import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.DateTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.Reservation;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 10/9/16.
 */
public class TablesGridViewFragment extends Fragment {
    private GridView tablesGridView;
    private TablesGridViewAdapter adapter;
    private Calendar dateToReserve;
    private HourTimeSlot timeSlotToReserve;

    public TablesGridViewFragment() {
    }

    public TablesGridViewFragment(TablesGridViewAdapter adapter, Calendar date, HourTimeSlot timeSlot) {
        this.adapter = adapter;
        this.dateToReserve = date;
        this.timeSlotToReserve = timeSlot;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tables_gridview, container, false);
        tablesGridView = (GridView)v.findViewById(R.id.reservation_tables_gridview);
        tablesGridView.setAdapter(adapter);
        tablesGridView.setOnItemClickListener(getTableClickListener());

        return v;
    }

    private AdapterView.OnItemClickListener getTableClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table selectedTable = (Table) tablesGridView.getItemAtPosition(position);
                if (selectedTable != null) {
                    //create a reservation
                    Reservation reservation = new Reservation(selectedTable, new DateTimeSlot(dateToReserve, timeSlotToReserve));
                    goToReservationDetails(reservation);
                }
            }
        };
    }

    private void goToReservationDetails(Reservation reservation) {
        Intent intent = new Intent(getActivity(), ReservationDetailsActivity.class);
        intent.putExtra(Constants.RESERVATION_DETAILS, reservation);
        startActivity(intent);
    }
}
