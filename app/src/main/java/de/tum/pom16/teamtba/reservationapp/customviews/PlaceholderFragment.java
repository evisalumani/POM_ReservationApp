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
    protected Restaurant restaurant;

    protected PlaceholderFragment() {
        super();
        setRetainInstance(true); //fragment instance is retained across Activity re-creation, e.g. data are not lost on rotation
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, Restaurant restaurant) {
        PlaceholderFragment fragment = null;
        switch(sectionNumber) {
            case 1:
                fragment = new RestaurantDetailsFragment();
                break;
            case 2:
                fragment = new RestaurantReviewsFragment();
                break;
            case 3:
                fragment = new RestaurantReservationFragment();
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
}