package de.tum.pom16.teamtba.reservationapp.dataaccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by evisa on 9/6/16.
 */
public class GlobalSearchFilters {
    private String location;
    private String cuisineType;
    private String priceCategory;
    private String ratings;
    private Calendar date;

    private Hashtable<SearchFilterType, FilterCriteria> filterCriteria;

    //singleton
    private static GlobalSearchFilters sharedInstance;

    private GlobalSearchFilters() {
        filterCriteria = new Hashtable<SearchFilterType, FilterCriteria>();
    }

    public static GlobalSearchFilters getSharedInstance() {
        if (sharedInstance == null)
            sharedInstance = new GlobalSearchFilters();
        return sharedInstance;
    }

    public void addSearchCriteria(SearchFilterType type, FilterCriteria criteria) {
        if (filterCriteria != null) {
            filterCriteria.put(type, criteria);
        }
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDateString() {
        if (date != null) {
            return date.get(Calendar.DAY_OF_MONTH) + "/" + ( date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
        }

        return "--/--/----";
    }
}
