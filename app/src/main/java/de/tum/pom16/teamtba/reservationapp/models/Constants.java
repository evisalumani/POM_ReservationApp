package de.tum.pom16.teamtba.reservationapp.models;

import java.util.HashMap;

/**
 * Created by evisa on 9/21/16.
 */
public abstract class Constants {
    //hockey app ID
    public static final String HOCKEY_APP_ID = "6d9d8d133e5a4faeaf057fa1c29bb630";

    //price
    public static final int PRICE_ALL = 0;
    public static final int PRICE_CHEAP = 1;
    public static final int PRICE_AVERAGE = 2;
    public static final int PRICE_ABOVE_AVERAGE = 3;
    public static final int PRICE_EXPENSIVE = 4;

    //sort by
    public static final int SORT_BY_DISTANCE = 0;
    public static final int SORT_BY_PRICE = 1;
    public static final int SORT_BY_RATING = 2;

    //intents
    public static final String RESERVATION_DETAILS = "RESERVATION_DETAILS";
    public static final String RESTAURANT_DETAILS = "RESTAURANT_DETAILS";

    public static String[] getSortByStrings() {
        return  new String[] { "Nearest Distance", "Lowest Price", "Highest Rating" };
    }

    public static String[] getPriceCategoryStrings() {
        return new String[] { "Any Price", "€", "€€", "€€€", "€€€€" };
    }
}
