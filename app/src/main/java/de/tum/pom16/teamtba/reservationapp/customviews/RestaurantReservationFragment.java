package de.tum.pom16.teamtba.reservationapp.customviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.dataaccess.SearchFilterType;
import de.tum.pom16.teamtba.reservationapp.dataaccess.TimeFilterCriteria;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;

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

    public RestaurantReservationFragment() {
        super();
        timeFilterCriteria = (TimeFilterCriteria) GlobalSearchFilters.getSharedInstance().getFilterCriteria().get(SearchFilterType.DATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_reservations, container, false);
        dateTextView = (TextView)v.findViewById(R.id.reservation_calendar_textview);
        timeTextView = (TextView)v.findViewById(R.id.reservation_time_textview);
        findTableButton = (Button)v.findViewById(R.id.reservation_find_table_button);

        setupTimeForSavedFilters();
        gridView = (GridView) v.findViewById(R.id.reservation_tables_gridview);

        adapter = new TablesGridViewAdapter(getActivity(), restaurant.getTables());
        gridView.setAdapter(adapter);
        return v;
    }

    private void setupTimeForSavedFilters() {
        Calendar date = (Calendar)timeFilterCriteria.getCriteria();
        HourTimeSlot timeSlot = timeFilterCriteria.getTimeSlot();
        if (date != null) {
            //set text
        }
    }
}
