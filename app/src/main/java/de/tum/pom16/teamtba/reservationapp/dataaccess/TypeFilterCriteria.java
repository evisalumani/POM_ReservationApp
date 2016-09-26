package de.tum.pom16.teamtba.reservationapp.dataaccess;

import com.annimon.stream.Collector;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Map;

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
        //criteria is Map<CuisineType, Boolean>
        Map<CuisineType, Boolean> criteriaMap = (Map<CuisineType, Boolean>)criteria;
        //Stream.of(criteriaMap.keySet()).filter(x -> criteriaMap.get(x) == true).collect(Collectors.toList());
        return criteriaMap.get(restaurant.getType()) ? true : false;
        //used this, because get() might return null too, in which case "false" is returned
        //return criteriaMap.get(restaurant.getType());
    }
}
