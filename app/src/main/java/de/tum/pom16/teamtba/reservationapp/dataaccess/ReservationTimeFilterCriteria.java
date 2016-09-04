package de.tum.pom16.teamtba.reservationapp.dataaccess;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 7/12/16.
 */
public class ReservationTimeFilterCriteria extends FilterCriteria {
    public ReservationTimeFilterCriteria(Object criteria) {
        super(criteria);
    }

    @Override
    public boolean filter(Restaurant restaurant) {
        int timeHour = (int) criteria;
        return timeHour >= restaurant.getOpeningHour() && timeHour <= restaurant.getClosingHour();
    }
}
