package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.location.Location;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 7/17/16.
 */
public class DataSort {
    protected List<Restaurant> restaurants; //model to sort
    protected boolean isAscending;

    public DataSort(boolean isAscending) {
        restaurants = new ArrayList<Restaurant>();
        this.isAscending = isAscending;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> sort() {
        return restaurants; //return restaurants unsorted
    }
}
