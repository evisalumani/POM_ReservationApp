package de.tum.pom16.teamtba.reservationapp.customviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/22/16.
 */
public class RestaurantDetailsFragment extends PlaceholderFragment {
    //model
    Restaurant restaurant;

    public RestaurantDetailsFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    //TODO: provide this implementation in base class;
    // create variable for the layout resource, to be initiated on constructor
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false);

    }
}
