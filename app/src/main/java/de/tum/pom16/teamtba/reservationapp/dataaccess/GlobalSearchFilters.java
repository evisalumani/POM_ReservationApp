package de.tum.pom16.teamtba.reservationapp.dataaccess;

import android.location.Location;
import android.provider.ContactsContract;

import com.annimon.stream.Stream;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/6/16.
 */
public class GlobalSearchFilters {
    private Map<CuisineType, Boolean> cuisines; //multi-select possible for cuisines
    private boolean isCurrentLocationChecked;
    private Location currentUserLocation;
    private Location locationToFilter; //could be current location or any other location
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

        //TODO: setup currentUserLocation
        isCurrentLocationChecked = true; //by default, search for restaurant near user's location

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
            filterCriteria.put(SearchFilterType.DATE, new TimeFilterCriteria(date));
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

    public Location getCurrentUserLocation() {
        return currentUserLocation;
    }

    public void setCurrentUserLocation(Location currentUserLocation) {
        this.currentUserLocation = currentUserLocation;
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

        //Note: restaurants need to be set separately as needed
        if (propertyToSortBy == Constants.SORT_BY_DISTANCE) {
            dataSort = new SortByDistance(true, null);
        } else if (propertyToSortBy == Constants.SORT_BY_PRICE) {
            dataSort = new SortByPrice(true, null);
        } else if (propertyToSortBy == Constants.SORT_BY_RATING) {
            dataSort = new SortByRating(false, null);
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
            //TODO:
            TimeFilterCriteria dateFilterCriteria = (TimeFilterCriteria) filterCriteria.get(SearchFilterType.DATE);
            if (dateFilterCriteria != null) {
                dateFilterCriteria.setTimeSlot(timeSlot);
            }
            //filterCriteria.put(SearchFilterType.TIMESLOT, new TimeSlotFilterCriteria(timeSlot));
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

    public Location getLocationToFilter() {
        return locationToFilter;
    }

    public void setLocationToFilter(Location locationToFilter) {
        this.locationToFilter = locationToFilter;
        if (filterCriteria != null) {
            //TODO: 15000 m -> create new variable
            filterCriteria.put(SearchFilterType.LOCATION, new DistanceFilterCriteria(15000, locationToFilter));
        }
    }

    public List<Restaurant> applyFilters() {
        //set distance from user
        //TODO: find another way of setting distance from user
        List<Restaurant> allRestaurants = DataGenerator.generateDummyData();
        Stream.of(allRestaurants).forEach(restaurant -> restaurant.setDistanceFromUserLocation(locationToFilter));

        //apply filtering
        return DataSearch.filter(allRestaurants, filterCriteria.values());
    }

    public boolean isCurrentLocationChecked() {
        return isCurrentLocationChecked;
    }

    public void setCurrentLocationChecked(boolean currentLocationChecked) {
        isCurrentLocationChecked = currentLocationChecked;
    }

    public DataSort getDataSort() {
        return dataSort;
    }

    public void setDataSort(DataSort dataSort) {
        this.dataSort = dataSort;
    }
}