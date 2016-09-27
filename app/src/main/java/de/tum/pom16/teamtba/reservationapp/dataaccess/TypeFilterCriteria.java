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
        
        //if "All" is selected -> return true
        if (criteriaMap.get(CuisineType.ALL)) {
            return true;
        }
        return criteriaMap.get(restaurant.getType()) ? true : false;
        //used ? :, because get() might return null too, in which case "false" is returned
    }
}
