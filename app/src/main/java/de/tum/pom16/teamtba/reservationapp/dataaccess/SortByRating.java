package de.tum.pom16.teamtba.reservationapp.dataaccess;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/21/16.
 */
public class SortByRating extends DataSort {
    public SortByRating(boolean isAscending) {
        super(isAscending);
    }

    @Override
    public List<Restaurant> sort() {
        return super.sort();
    }
}
