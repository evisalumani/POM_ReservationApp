package de.tum.pom16.teamtba.reservationapp.dataaccess;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

/**
 * Created by evisa on 6/1/16.
 */
public class TypeFilterCriteria extends FilterCriteria {
    public TypeFilterCriteria(Object criteria) {
        super(criteria);
    }

    @Override
    public boolean filter(Restaurant restaurant) {
        return restaurant.getType() == (CuisineType)criteria;
    }
}
