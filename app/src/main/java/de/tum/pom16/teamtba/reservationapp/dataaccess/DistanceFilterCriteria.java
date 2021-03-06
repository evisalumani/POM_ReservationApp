package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.location.*;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 5/31/16.
 */
public class DistanceFilterCriteria extends FilterCriteria {
    private Location userLocation;
    private float distanceInMeters = 15000; //15 km at least
    //search within a radius
    public DistanceFilterCriteria(Object criteria) {
        super(criteria); //criteria is the upper distance from user
        userLocation = GlobalSearchFilters.getSharedInstance().getCurrentUserLocation();
    }

    public DistanceFilterCriteria(Object criteria, Location userLocation) {
        this(criteria);
        this.userLocation = userLocation;
    }

    public boolean filter(Restaurant restaurant) {
        if (userLocation != null) {
            return (userLocation.distanceTo(restaurant.getGpsLocation()) <= (float) this.distanceInMeters);
        }
        return false;
    }
}
