package de.tum.pom16.teamtba.reservationapp.utilities;

import android.location.Location;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.android.gms.location.places.Place;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String[] getDaysOfWeekString() {
        return new String[] { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }

    public static String getDateString(Calendar date) {
        if (date != null) {
            return date.get(Calendar.DAY_OF_MONTH) + "/" + ( date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
        }

        return "--/--/----";
    }

    public static String getMonthString(Calendar calendar) {
        return calendar == null? null :new SimpleDateFormat("MMM").format(calendar.getTime()); //short description (e.g. OCT)
    }

    public static int getDate(Calendar calendar) {
        return calendar == null ? 0 : calendar.get(Calendar.DATE);
    }

    public static String getDayOfWeekString(Calendar calendar) {
        return calendar == null ? null : getDaysOfWeekString()[calendar.get(Calendar.DAY_OF_WEEK)];
    }
}
