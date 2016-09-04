package de.tum.pom16.teamtba.reservationapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hamed on 18/06/16.
 */

public class Table implements Parcelable {
    //TODO: redundant properties; have a Restaurant reference (but then, Parcelable behaves weirdly and run-time errors occur)
    //Note: assume reservations are valid for 1 hour
    private int tableId;
    private int openingHour;
    private int closingHour;
    private int openingHoursDuration;
    private int tableCapacity;
    boolean[] reservationStatus;

    public Table(int tableId, int capacity) {
        this.tableId = tableId;
        this.tableCapacity = capacity;
    }

    public Table(int tableId, int capacity, int openingHour, int closingHour) {
        this(tableId, capacity);

        this.openingHour = openingHour;
        this.closingHour = closingHour;
        openingHoursDuration = closingHour - openingHour;
        reservationStatus = new boolean[openingHoursDuration];
        for(int i = 0; i < openingHoursDuration; ++i) {
            //random reservation status
            int statusInt = (int)(Math.random() * 2);
            Boolean status;
            if (statusInt == 0) {
                status = Boolean.FALSE;
            } else {
                status = Boolean.TRUE;
            }
            reservationStatus[i]= status;
        }
    }

    public CalendarEvent setReservation(int index) {
        if (index >= 0 && index < openingHoursDuration) {
            CalendarEvent event = null;
            if (reservationStatus[index] == Boolean.FALSE) {
                reservationStatus[index] = Boolean.TRUE;

                //create CalendarEvent (duration 1 hr)
                event = new CalendarEvent(getTableId(), getCapacity(), openingHour + index, openingHour + index + 1);
            }
            return event;
        } else
            throw new IndexOutOfBoundsException();
    }

    public Boolean getReservation(int index) {
        if (index >= 0 && index < openingHoursDuration)
            return reservationStatus[index];
        else
            throw new IndexOutOfBoundsException();
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

    public void setOpeningHoursDuration(int openingHoursDuration) {
        this.openingHoursDuration = openingHoursDuration;
    }

    public void setReservationStatus(boolean[] reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tableId);
        dest.writeInt(openingHour);
        dest.writeInt(closingHour);
        dest.writeInt(openingHoursDuration);
        dest.writeBooleanArray(reservationStatus);
        dest.writeInt(tableCapacity);

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
        openingHour = in.readInt();
        closingHour = in.readInt();
        openingHoursDuration = in.readInt();
        reservationStatus = new boolean[openingHoursDuration];
        in.readBooleanArray(reservationStatus);
        tableCapacity = in.readInt();

    }

}

