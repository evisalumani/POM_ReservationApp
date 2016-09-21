package de.tum.pom16.teamtba.reservationapp.dataaccess;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/21/16.
 */
public class TimeSlotFilterCriteria extends FilterCriteria {
    // TODO: implement
    public TimeSlotFilterCriteria(Object criteria) {
        super(criteria);
    }

    @Override
    public boolean filter(Restaurant restaurant) {
        return true;
    }
}
