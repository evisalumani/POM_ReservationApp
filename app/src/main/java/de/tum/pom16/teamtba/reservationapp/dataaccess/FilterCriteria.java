package de.tum.pom16.teamtba.reservationapp.dataaccess;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 5/31/16.
 */
public abstract class FilterCriteria {
    public Object criteria; //can be number, String, enum etc.
    public abstract boolean filter(Restaurant restaurant); //can be later a generic

    public FilterCriteria(Object criteria) {
        this.criteria = criteria;
    }
}
