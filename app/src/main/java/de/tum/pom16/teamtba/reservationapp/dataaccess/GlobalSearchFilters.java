package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.text.BoringLayout;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

/**
 * Created by evisa on 9/6/16.
 */
public class GlobalSearchFilters {
    private Map<CuisineType, Boolean> cuisines; //multi-select possible for cuisines
    private String location;
    private int priceCategory;
    private Calendar date;
    //time
    private int propertyToSortBy;

    private Hashtable<SearchFilterType, FilterCriteria> filterCriteria;
    private DataSort dataSort;

    //singleton
    private static GlobalSearchFilters sharedInstance;

    private GlobalSearchFilters() {
        cuisines = new LinkedHashMap<>(); //linked hashmap preserves the order of insertion
        filterCriteria = new Hashtable<SearchFilterType, FilterCriteria>();

        //setup cuisines
        setupCuisines();

        //setup location

        //setup price
        setupPrice();

        //setup rating

        //setup date

        //setup time

        //setup sort by

    }

    private void setupCuisines() {
        if (cuisines != null) {
            for (CuisineType cuisine : CuisineType.values()) {
                boolean defaultSelected = cuisine == CuisineType.ALL ? true : false;
                cuisines.put(cuisine, defaultSelected);
            }
        }
    }

    private void setupPrice() {
        priceCategory = 2; //average
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(int priceCategory) {
        this.priceCategory = priceCategory;
    }

    public Map<CuisineType, Boolean> getCuisines() {
        return cuisines;
    }

    public void setCuisines(Map<CuisineType, Boolean> cuisines) {
        this.cuisines = cuisines;
    }

    public void setCuisineChoice(CuisineType cuisineType, boolean isSelected) {
        if (cuisines != null) {
            cuisines.put(cuisineType, isSelected); //replaces old value for the existing key
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
}
