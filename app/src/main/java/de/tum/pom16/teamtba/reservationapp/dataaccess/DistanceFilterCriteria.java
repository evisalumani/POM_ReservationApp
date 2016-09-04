package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.location.*;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 5/31/16.
 */
public class DistanceFilterCriteria extends FilterCriteria {
    private Location userLocation;
    //search within a radius
    public DistanceFilterCriteria(Object criteria) {
        super(criteria);
    }

    public DistanceFilterCriteria(Object criteria, Location userLocation) {
        this(criteria);
        this.userLocation = userLocation;
    }

    public boolean filter(Restaurant restaurant) {
        if (userLocation != null) {
            return (userLocation.distanceTo(restaurant.getGpsLocation()) <= (float) this.criteria);
        }
        return true; //for simplicity
    }
}
