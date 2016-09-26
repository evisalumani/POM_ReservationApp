package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.location.Location;

import com.google.android.gms.wearable.DataApi;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;

/**
 * Created by evisa on 9/6/16.
 */
public class GlobalSearchFilters {
    private Map<CuisineType, Boolean> cuisines; //multi-select possible for cuisines
    private Location location;
    private int maxPriceCategory;
    private Calendar date;
    private HourTimeSlot timeSlot;
    private int propertyToSortBy;

    private Hashtable<SearchFilterType, FilterCriteria> filterCriteria;
    private DataSort dataSort;

    //singleton
    private static GlobalSearchFilters sharedInstance;

    //set up default filters
    private GlobalSearchFilters() {
        filterCriteria = new Hashtable<SearchFilterType, FilterCriteria>();

        //setup cuisines
        setupCuisines(); //"All" by default -> no filter on cuisines

        //TODO: setup location

        //price -> 0 by default -> no filter on price

        //date -> current data
        setDate(Calendar.getInstance());

        //time - null by default -> no filter on time

        //sort by
        setPropertyToSortBy(Constants.SORT_BY_DISTANCE);
    }

    private void setupCuisines() {
        cuisines = new LinkedHashMap<>(); //linked hashmap preserves the order of insertion
        if (cuisines != null) {
            for (CuisineType cuisine : CuisineType.values()) {
                boolean defaultSelected = cuisine == CuisineType.ALL ? true : false;
                cuisines.put(cuisine, defaultSelected);
            }
        }
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

        if (filterCriteria != null) {
            filterCriteria.put(SearchFilterType.DATE, new DateFilterCriteria(date));
        }
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMaxPriceCategory() {
        return maxPriceCategory;
    }

    public void setMaxPriceCategory(int maxPriceCategory) {
        this.maxPriceCategory = maxPriceCategory;

        if (filterCriteria != null) {
            filterCriteria.put(SearchFilterType.PRICE_CATEGORY, new PriceFilterCriteria(maxPriceCategory));
        }
    }

    public Map<CuisineType, Boolean> getCuisines() {
        return cuisines;
    }

    public void setCuisines(Map<CuisineType, Boolean> cuisines) {
        this.cuisines = cuisines;
    }

    public void setCuisineChoice(CuisineType cuisineType, boolean isSelected) {
        if (cuisines != null && filterCriteria != null) {
            cuisines.put(cuisineType, isSelected); //replaces old value for the existing key

            filterCriteria.put(SearchFilterType.CUISINE_TYPE, new TypeFilterCriteria(cuisines));
        }
    }

    public int getPropertyToSortBy() {
        return propertyToSortBy;
    }

    public void setPropertyToSortBy(int propertyToSortBy) {
        this.propertyToSortBy = propertyToSortBy;

        if (propertyToSortBy == Constants.SORT_BY_DISTANCE) {
            //TODO: add user location to constructor
            dataSort = new SortByDistance(true, null);
        } else if (propertyToSortBy == Constants.SORT_BY_PRICE) {
            dataSort = new SortByPrice(true);
        } else if (propertyToSortBy == Constants.SORT_BY_RATING) {
            dataSort = new SortByRating(false);
        } else {
            dataSort = null;
        }
    }

    public HourTimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(HourTimeSlot timeSlot) {
        this.timeSlot = timeSlot;

        if (filterCriteria != null) {
            filterCriteria.put(SearchFilterType.TIMESLOT, new TimeSlotFilterCriteria(timeSlot));
        }
    }

    public Hashtable<SearchFilterType, FilterCriteria> getFilterCriteria() {
        return filterCriteria;
    }

    public int getNrOfSpecificCuisinesSelected() {
        int nrOfSpecificCuisinesSelected = 0;

        if (cuisines != null) {
            if (cuisines.get(CuisineType.ALL)) return 0;

            for (boolean b : cuisines.values()) {
                if (b) nrOfSpecificCuisinesSelected++;
            }
        }

        return nrOfSpecificCuisinesSelected;
    }
}
