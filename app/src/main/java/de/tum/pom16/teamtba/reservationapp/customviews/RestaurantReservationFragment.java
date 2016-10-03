package de.tum.pom16.teamtba.reservationapp.customviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tum.pom16.teamtba.reservationapp.R;

/**
 * Created by evisa on 9/22/16.
 */
public class RestaurantReservationFragment extends PlaceholderFragment {
    public RestaurantReservationFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_reservations, container, false);
    }
}
