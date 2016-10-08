package de.tum.pom16.teamtba.reservationapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    Hashtable<Integer, OpeningTimes> openingTimes = new Hashtable<Integer, OpeningTimes>();
    //Hashtable<DateTimeSlot, Boolean> tableReservationStatus = new Hashtable<DateTimeSlot, Boolean>();
    List<Reservation> reservations = new ArrayList<Reservation>();

    public Table(int tableId, int capacity) {
        this.tableId = tableId;
        this.tableCapacity = capacity;
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

    public void setOpeningTimes(Hashtable<Integer, OpeningTimes> openingTimes) {
        this.openingTimes = openingTimes;
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