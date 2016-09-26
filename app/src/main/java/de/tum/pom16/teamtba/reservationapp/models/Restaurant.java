package de.tum.pom16.teamtba.reservationapp.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.annimon.stream.Stream;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by evisa on 5/24/16.
 */
public class Restaurant implements Parcelable {

    private String name;
    private String description;
    private String address;
    private Location gpsLocation;
    private CuisineType type;
    private double averagePrice;
    private int priceCategory;
    private float averageRating;
    private int openingHour;
    private int closingHour;
    private float distanceFromUserLocation;

    private List<Table> tables = new ArrayList<Table>();
    private List<RestaurantReview> reviews = new ArrayList<RestaurantReview>();
    private Hashtable<Integer, OpeningTimes> openingTimes = new Hashtable<>(); //Integer for day_of_week

    private int tablesNumber;

    public Restaurant(String name, String description, String address, double latitude, double longitude, CuisineType type, double averagePrice, int priceCategory, int openingHour, int closingHour, int inTablesNumber) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.gpsLocation = new Location(name); //location provider on the constructor
        this.gpsLocation.setLatitude(latitude);
        this.gpsLocation.setLongitude(longitude);
        this.type = type;
        this.averagePrice = averagePrice;
        this.tablesNumber = inTablesNumber;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        //TODO: data validation

        for (int i = 0; i < tablesNumber; ++i) {
            int randomNum = 4 + (int)(Math.random() * 4); //4, 5, 6, 7, 8 possible capacity
            tables.add(new Table(i+1, randomNum, openingHour, closingHour));
        }
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

    public int getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(int openingHour) {
        this.openingHour = openingHour;
    }

    public int getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(int closingHour) {
        this.closingHour = closingHour;
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

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getPriceCategoryStr() {
        if (averagePrice < 10) {
            return "€";
        } else if (averagePrice < 50) {
            return "€€";
        } else {
            return "€€€";
        }
    }

    public void setOpeningTimes(int dayOfWeek, int openingHour, int closingHour) {
        if (openingTimes != null) {
            openingTimes.put(dayOfWeek, new OpeningTimes(openingHour, closingHour));
        }
    }

    public Hashtable<Integer, OpeningTimes> getOpeningTimes() {
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
            avg = sum / getReviewsNr();
        }

        return avg;
    }

    public int getReviewsNr() {
        if (reviews != null) {
            return reviews.size();
        }
        return 0;
    }

    public float getDistanceFromUserLocation() {
        return distanceFromUserLocation;
    }

    public void setDistanceFromUserLocation(Location userLocation) {
        this.distanceFromUserLocation = getGpsLocation().distanceTo(userLocation);
    }

    public void setAverageRating(float averageRating) { this.averageRating = averageRating; }

    public String getShortDescription() {
        //format distance (in km) to 1 decimal place
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);

        return type.name() + ", " + getPriceCategoryStr() + ", " + df.format(getDistanceFromUserLocation() / 1000) + " km away";
    }

    public String getLongDescription() {
        return "Type: " + this.type + ", Rating: " + getAverageRating() + "\nOpening Hours: " + String.valueOf(openingHour) +  " - " + String.valueOf(closingHour);
    }

    public void addReview(RestaurantReview review) {
        if (reviews != null) {
            reviews.add(review);
        }
    }

    public List<RestaurantReview> getReviews() {
        return reviews;
    }

    public List<Table> getTables(){
        return tables;
    }

    //TODO: fix to include both starting and ending hour
    public Integer[] getTimeSlots() {
        Integer[] timeSlots = new Integer[closingHour-openingHour];
        for (int i=0; i<timeSlots.length; i++) {
            timeSlots[i] = openingHour + i;
        }

        return timeSlots;
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
        dest.writeDouble(averagePrice);
        dest.writeFloat(getAverageRating());
        dest.writeTypedList(tables);
        //reviews
        dest.writeTypedList(reviews);
        dest.writeInt(tablesNumber);
        dest.writeInt(openingHour);
        dest.writeInt(closingHour);
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
            averagePrice = in.readDouble();
            averageRating = in.readFloat();
            in.readTypedList(tables,Table.CREATOR);
            //try adding reviews as tables
            in.readTypedList(reviews,RestaurantReview.CREATOR);
            tablesNumber = in.readInt();
            openingHour = in.readInt();
            closingHour = in.readInt();
        }
}

