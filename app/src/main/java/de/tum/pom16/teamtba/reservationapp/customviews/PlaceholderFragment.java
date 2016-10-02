package de.tum.pom16.teamtba.reservationapp.customviews;

/**
 * Created by evisa on 9/22/16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    //model
    private int fragmentResource;
    private Restaurant restaurant;

    protected PlaceholderFragment(int fragmentResource) {
        this.fragmentResource = fragmentResource;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, Restaurant restaurant) {
        PlaceholderFragment fragment = null;
        switch(sectionNumber) {
            case 1:
                fragment = new RestaurantDetailsFragment(R.layout.fragment_restaurant_details);
                break;
            case 2:
                fragment = new RestaurantReviewsFragment(R.layout.fragment_restuarant_reviews);
                break;
            case 3:
                fragment = new RestaurantReservationFragment(R.layout.fragment_restaurant_reservations);
                break;
        }

        fragment.setRestaurant(restaurant);
        return fragment;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(fragmentResource, container, false);
    }
}