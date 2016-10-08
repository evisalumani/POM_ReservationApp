package de.tum.pom16.teamtba.reservationapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by hamed on 18/06/16.
 */

public class Table implements Parcelable {
    //TODO: redundant properties; have a Restaurant reference (but then, Parcelable behaves weirdly and run-time errors occur)
    //Note: assume reservations are valid for 1 hour
    private int tableId;
    private int tableCapacity;
    private List<Reservation> reservations = new ArrayList<Reservation>();

    //restaurant properties needed for the reservation details
    private String restaurantName;
    private String restaurantAddress;
    private HashMap<Integer, OpeningTimes> restaurantOpeningTimes = new HashMap<Integer, OpeningTimes>();
    //Hashtable<DateTimeSlot, Boolean> tableReservationStatus = new Hashtable<DateTimeSlot, Boolean>();

    public Table(int tableId, int capacity, Restaurant restaurant) {
        this.tableId = tableId;
        this.tableCapacity = capacity;
        this.restaurantName = restaurant.getName();
        this.restaurantAddress = restaurant.getAddress();
        this.restaurantOpeningTimes = restaurant.getOpeningTimes();
    }

    public int getCapacity() {
        return tableCapacity;
    }

    public void setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setOpeningTimes(HashMap<Integer, OpeningTimes> openingTimes) {
        this.restaurantOpeningTimes = openingTimes;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tableId);
        dest.writeInt(tableCapacity);
        dest.writeTypedList(reservations);
        dest.writeString(restaurantName);
        dest.writeString(restaurantAddress);

        //write opening times
        dest.writeInt(restaurantOpeningTimes.size());
        for (HashMap.Entry<Integer, OpeningTimes> entry : restaurantOpeningTimes.entrySet()) {
            dest.writeInt(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    // Creator
    public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    private Table(Parcel in) {
        tableId = in.readInt();
        tableCapacity = in.readInt();
        in.readTypedList(reservations, Reservation.CREATOR);
        restaurantName = in.readString();
        restaurantAddress = in.readString();

        //read opening times
        int size = in.readInt();
        restaurantOpeningTimes = new HashMap<Integer, OpeningTimes>();
        for (int i = 0; i < size; i++) {
            Integer key = in.readInt();
            OpeningTimes value = in.readParcelable(OpeningTimes.class.getClassLoader());
            restaurantOpeningTimes.put(key, value);
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        if (reservation != null) reservations.add(reservation);
    }
}