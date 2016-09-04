package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.location.Location;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 7/17/16.
 */
public class DataSort {
    public static final int TOP_RESULTS = 100;

    public static List<Restaurant> sortRestaurantsByDistanceFromUser(List<Restaurant> restaurants, Location userLocation) {
        //set distance from user to each restaurant
        Stream.of(restaurants).forEach(restaurant -> restaurant.setDistanceFromUserLocation(userLocation));

        //sort by distance
        restaurants = Stream.of(restaurants).sorted((r1, r2) -> Float.compare(r1.getDistanceFromUserLocation(), r2.getDistanceFromUserLocation())).collect(Collectors.toList());
        return restaurants;
    }
}
