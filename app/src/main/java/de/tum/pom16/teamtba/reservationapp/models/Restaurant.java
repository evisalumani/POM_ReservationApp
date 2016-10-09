package de.tum.pom16.teamtba.reservationapp.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.annimon.stream.Collector;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by evisa on 5/24/16.
 */
public class Restaurant implements Parcelable {

    private String name;
    private String description;
    private String address;
    private Location gpsLocation;
    private CuisineType type;
    private int priceCategory;
    private float averageRating;
    private float distanceFromUserLocation;

    private List<Table> tables = new ArrayList<Table>();
    private List<RestaurantReview> reviews = new ArrayList<RestaurantReview>();
    private HashMap<Integer, OpeningTimes> openingTimes = new HashMap<>(); //Integer for day_of_week
    //Calendar.SUNDAY is 1, Calendar.MONDAY is 2 and so on...

    public Restaurant(String name, String description, String address, double latitude, double longitude, CuisineType type, int priceCategory) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.gpsLocation = new Location(name); //location provider on the constructor
        this.gpsLocation.setLatitude(latitude);
        this.gpsLocation.setLongitude(longitude);
        this.type = type;
        this.priceCategory = priceCategory;
        //TODO: data validation
    }

    public void setPriceCategory(int category) {
        this.priceCategory = category;
    }

    public int getPriceCategory() {
        return this.priceCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getGpsLocation() {
        return gpsLocation;
    }

    public double getLatitude() { return this.gpsLocation.getLatitude(); }

    public void setLatitude(double latitude) { this.gpsLocation.setLatitude(latitude); }

    public double getLongitude() { return this.gpsLocation.getLongitude(); }

    public void setLongitude(double longitude) { this.gpsLocation.setLongitude(longitude); }

    public CuisineType getType() {
        return type;
    }

    public void setType(CuisineType type) {
        this.type = type;
    }

    public String getPriceCategoryStr() {
        switch (priceCategory) {
            case 1: return "€";
            case 2: return "€€";
            case 3: return "€€€";
            case 4: return "€€€€";
            default: return "";
        }
    }

    public int[] getReviewsDistribution() {
        if (reviews != null && reviews.size() > 0) {
            int[] reviewsDistribution = new int[5]; //for 5 stars
            for (int i = 0; i < 5; i++) {
                final float j = i + 1;
                reviewsDistribution[i] = (int) Stream.of(reviews)
                        .filter(review -> review.getRating() == j)
                        .count();
            }

            return reviewsDistribution;
        }

        return null;
    }

    public void setOpeningTimes(int dayOfWeek, HourTimeSlot openingHour, HourTimeSlot closingHour) {
        if (openingTimes != null) {
            openingTimes.put(dayOfWeek, new OpeningTimes(openingHour, closingHour));
        }
    }

    public HashMap<Integer, OpeningTimes> getOpeningTimes() {
        return openingTimes;
    }

    public OpeningTimes getOpeningTimes(int dayOfWeek) {
        //Note: if null is returned, than the restaurant is closed at that day of the week
        return openingTimes.get(dayOfWeek);
    }

    public float getAverageRating() {
        float avg = (float) 0.0;
        if (reviews != null && getReviewsNr() > 0) {
            float sum = Stream.of(reviews).map(review -> review.getRating()).reduce((float) 0.0, (a, b) -> a + b);
            avg = sum/getReviewsNr();
        }

        return (float) (Math.round(avg * 10d)/10d); //round to 1 decimal place
    }

    public int getReviewsNr() {
        if (reviews != null) {
            return reviews.size();
        }
        return 0;
    }

    public float getDistanceFromUserLocation() {
        //returns distance in meters
        return distanceFromUserLocation;
    }

    public double getRoundedDistanceFromUserLocation() {
        //returns distance in km, rounded to 2 decimal places
        return Math.round((distanceFromUserLocation/1000.0) * 100.0) / 100.0;
    }

    public void setDistanceFromUserLocation(Location userLocation) {
        this.distanceFromUserLocation = getGpsLocation().distanceTo(userLocation);
    }

    public void setAverageRating(float averageRating) { this.averageRating = averageRating; }

    //TODO: remove this, instead redesign the adapter for the search-results
    public String getShortDescription() {
        //format distance (in km) to 1 decimal place
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);

        return type.name() + ", " + getPriceCategoryStr() + ", " + getRoundedDistanceFromUserLocation() + " km away";
    }

    public void addReview(RestaurantReview review) {
        if (reviews != null) {
            reviews.add(review);
        }
    }

    public void addTable(Table table) {
        if (tables != null) {
            tables.add(table);
        }
    }

    public boolean isOpenAtTimeSlot(Calendar date, HourTimeSlot timeSlot) {
        //if the value for the key "dayOfWeek" is null -> restaurant doesn't have any opening times at that day
        //Calendar.DAY_OF_WEEK starting from 1 to 7
        OpeningTimes openingTimesForDayOfWeek = openingTimes.get(date.get(Calendar.DAY_OF_WEEK));
        if (openingTimesForDayOfWeek == null) {
            return false; //not even open on this day
        }

        //check if within opening times
        //if earlier than opening, and later than closing -> return false
        if (timeSlot.compareTo(openingTimesForDayOfWeek.getOpeningTimeSlot()) < 0 || timeSlot.compareTo(openingTimesForDayOfWeek.getLastAvailableReservationSlot()) > 0) {
            return false;
        }

        return true;
    }

    public boolean isOpenAtDayOfWeek(Calendar date) {
        //date.get(Calendar.DAY_OF_WEEK) returns 1 for Sunday, 2 for Monday and so on...
        return openingTimes.get(date.get(Calendar.DAY_OF_WEEK)) != null;
    }

    //TODO: how to group days having the same opening times
    public HashMap<OpeningTimes, ArrayList<Integer>>aggregateOpeningTimes() {
        HashMap<OpeningTimes, ArrayList<Integer>> result = new HashMap<OpeningTimes, ArrayList<Integer>>();

        for (Map.Entry<Integer, OpeningTimes> entry :openingTimes.entrySet()) {
            if (result.get(entry.getValue()) != null) {
                //OpeningTime is already contained
                ArrayList<Integer> existingList = result.get(entry.getValue());
                existingList.add(entry.getKey());
            } else {
                //OpeningTimes not contained
                ArrayList<Integer> newList = new ArrayList<Integer>();
                newList.add(entry.getKey());
                result.put(entry.getValue(), newList);
            }
        }

        return result;
        //returns the same structure as using Google Guava library
        //HashMultimap<OpeningTimes, Integer> multiMap = Multimaps.invertFrom(Multimaps.forMap(openingTimes), HashMultimap.<OpeningTimes, Integer> create());
    }

    public List<RestaurantReview> getReviews() {
        return reviews;
    }

    public List<Table> getTables(){
        return tables;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(address);
        gpsLocation.writeToParcel(dest, flags);
        dest.writeString(type.name());
        dest.writeInt(priceCategory);
        dest.writeFloat(getAverageRating());
        dest.writeFloat(distanceFromUserLocation);
        dest.writeTypedList(tables);
        //reviews
        dest.writeTypedList(reviews);

        dest.writeInt(openingTimes.size());
        for (HashMap.Entry<Integer, OpeningTimes> entry : openingTimes.entrySet()) {
            dest.writeInt(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    // Creator
    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

        private Restaurant(Parcel in) {
            name = in.readString();
            description = in.readString();
            address = in.readString();
            gpsLocation = Location.CREATOR.createFromParcel(in);
            type = CuisineType.valueOf(in.readString());
            //averagePrice = in.readDouble();
            priceCategory = in.readInt();
            averageRating = in.readFloat();
            distanceFromUserLocation = in.readFloat();
            in.readTypedList(tables,Table.CREATOR);
            in.readTypedList(reviews,RestaurantReview.CREATOR);

            //get opening times
            int size = in.readInt();
            openingTimes = new HashMap<Integer, OpeningTimes>();
            for (int i = 0; i < size; i++) {
                Integer key = in.readInt();
                OpeningTimes value = in.readParcelable(OpeningTimes.class.getClassLoader());
                openingTimes.put(key, value);
            }
        }

    public List<Table> getAvailableTables(Calendar date, HourTimeSlot timeSlot) {
        if (date == null || timeSlot == null) {
            return null;
        }

        if (!isOpenAtTimeSlot(date, timeSlot)) {
            return null;
        }

        DateTimeSlot dateTimeSlot = new DateTimeSlot(date, timeSlot);
        return Stream.of(tables).filter(table ->
                //either there are no reservations for the table, or there are no reservations at the given time slot
                table.getReservations() == null
                        || table.getReservations().size() == 0
                        || Stream.of(table.getReservations()).noneMatch(reservation -> reservation.getDateTimeSlot() == dateTimeSlot))
                .collect(Collectors.toList());
    }
}