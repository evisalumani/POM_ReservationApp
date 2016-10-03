package de.tum.pom16.teamtba.reservationapp.utilities;

import android.location.Location;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.android.gms.location.places.Place;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/13/16.
 */
public abstract class Helpers {
    public static Location getLocationFromPlace(Place place) {
        if (place != null && place.getLatLng() != null) {
            Location location = new Location("");
            location.setLatitude(place.getLatLng().latitude);
            location.setLongitude(place.getLatLng().longitude);
            return location;
        }
        return null;
    }

    public static List<Restaurant> deepCopyRestaurants(List<Restaurant> restaurants) {
        return Stream.of(restaurants).collect(Collectors.toList());
    }

    public static String[] getDayOfWeekString() {
        return new String[] { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }
}
