package de.tum.pom16.teamtba.reservationapp.utilities;

import android.location.Location;

import com.google.android.gms.location.places.Place;

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
}
