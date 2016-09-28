package de.tum.pom16.teamtba.reservationapp.dataaccess;

import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 6/1/16.
 */
public class PriceFilterCriteria extends FilterCriteria {
    // TODO: implement
    public PriceFilterCriteria(Object criteria) {
        super(criteria);
    }

    @Override
    public boolean filter(Restaurant restaurant) {
        //at most a certain price
        int priceCategory = (int) criteria;
        //if all prices are accepted, or the max expensive option is chosen -> return true
        if (priceCategory != Constants.PRICE_ALL && priceCategory != Constants.PRICE_EXPENSIVE) {
            //return
            return restaurant.getPriceCategory() <= priceCategory;
        }

        return true;
    }
}
