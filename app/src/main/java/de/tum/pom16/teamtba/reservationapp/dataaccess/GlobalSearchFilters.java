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

import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

/**
 * Created by evisa on 9/6/16.
 */
public class GlobalSearchFilters {
    private String cuisineType;
    private String location;
    private String priceCategory;
    private String ratings;
    private Calendar date;
    //time
    //sort by

    private Map<CuisineType, Boolean> cuisines; //multi-select possible for cuisines
    private Hashtable<SearchFilterType, FilterCriteria> filterCriteria;

    //singleton
    private static GlobalSearchFilters sharedInstance;

    private GlobalSearchFilters() {
        cuisines = new LinkedHashMap<>(); //linked hashmap preserves the order of insertion
        filterCriteria = new Hashtable<SearchFilterType, FilterCriteria>();
        setupCuisines();
    }

    private void setupCuisines() {
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

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(String priceCategory) {
        this.priceCategory = priceCategory;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
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

}
