package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.location.Location;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/21/16.
 */
public class SortByDistance extends DataSort {
    private Location userLocation;

    public SortByDistance(boolean isAscending, List<Restaurant> restaurants, Location location) {
        super(isAscending, restaurants);
        this.userLocation = location;
    }

    @Override
    public List<Restaurant> sort() {
        //TODO: make use of isAscending
        //TODO: set distance from user as soon as location is set?
        //set distance from user to each restaurant
        Stream.of(restaurants).forEach(restaurant -> restaurant.setDistanceFromUserLocation(userLocation));

        //sort by distance
        restaurants = Stream.of(restaurants)
                .sorted((r1, r2) -> Float.compare(r1.getDistanceFromUserLocation(), r2.getDistanceFromUserLocation()))
                .collect(Collectors.toList());
        return restaurants;
    }
}
