package de.tum.pom16.teamtba.reservationapp.dataaccess;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 5/31/16.
 */
public class DataSearch {
    //apply multiple filters
    public static List<Restaurant> filter(List<Restaurant> data, List<FilterCriteria> criteria) {
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

    //return non-reserved tables for a given timeslot
    public static List<Table> filterTablesByTimeSlot(Restaurant restaurant, int timeSlot) {
        //TODO: check timeSlot between opening and closing time
        return Stream.of(restaurant.getTables()).filter(table -> !table.getReservation(timeSlot)).collect(Collectors.toList());
        //return Stream.of(restaurant.getTables()).filter(t => !t.getReservation(timeSlot)).collect(Collectors.toList());
    }

    public static Optional<Restaurant> filterRestaurantByName(List<Restaurant> restaurants, String restaurantName) {
        return Stream.of(restaurants).filter(restaurant -> restaurant.getName().equalsIgnoreCase(restaurantName)).findFirst();
    }
}
