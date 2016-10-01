package de.tum.pom16.teamtba.reservationapp.dataaccess;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/21/16.
 */
public class SortByRating extends DataSort {
    public SortByRating(boolean isAscending, List<Restaurant> restaurants) {
        super(isAscending, restaurants);
    }

    @Override
    public List<Restaurant> sort() {
        restaurants = Stream.of(restaurants)
                .sorted((r1, r2) -> Float.compare(r2.getAverageRating(), r1.getAverageRating()))
                .collect(Collectors.toList());
        return restaurants;
    }
}
