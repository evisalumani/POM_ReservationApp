package de.tum.pom16.teamtba.reservationapp.dataaccess;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 5/31/16.
 */
public class DataSearch {
    //apply multiple filters
    public static List<Restaurant> filter(List<Restaurant> data, Collection<FilterCriteria> criteria) {
        List<Restaurant> filteredRestaurants = new ArrayList<Restaurant>();

        for (Restaurant restaurant : data) {
            boolean isCriteriaFulfilled = true;
            for (FilterCriteria criterion : criteria) {
                if (!criterion.filter(restaurant)) {
                    isCriteriaFulfilled = false;
                    break;
                }
            }

            if (isCriteriaFulfilled) {
                filteredRestaurants.add(restaurant);
            }
        }
        return filteredRestaurants;
    }

    public static List<Restaurant> filterRestaurantContainingString(List<Restaurant> restaurants, String queryString) {
        return Stream.of(restaurants).filter(restaurant -> restaurant.getName().toLowerCase().contains(queryString.toLowerCase())).collect(Collectors.toList());
    }
}
