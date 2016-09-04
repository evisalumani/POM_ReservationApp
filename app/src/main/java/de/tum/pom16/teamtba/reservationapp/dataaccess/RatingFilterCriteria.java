package de.tum.pom16.teamtba.reservationapp.dataaccess;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 6/1/16.
 */
public class RatingFilterCriteria extends FilterCriteria {
    // TODO: implement
    public RatingFilterCriteria(Object criteria) {
        super(criteria);
    }

    @Override
    public boolean filter(Restaurant restaurant) {
        //at least a certain rating
        return restaurant.getAverageRating() >= (float)criteria;
    }
}
