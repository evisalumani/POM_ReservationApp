package de.tum.pom16.teamtba.reservationapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by evisa on 9/27/16.
 */
public class Reservation implements Parcelable {
    private Table table; //table links to restaurant
    private DateTimeSlot dateTimeSlot;
    //TODO: is confirmed for later (use notifications)
    private boolean isConfirmed;

    public Reservation(Table table, DateTimeSlot dateTimeSlot) {
        this.table = table;
        this.dateTimeSlot = dateTimeSlot;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public DateTimeSlot getDateTimeSlot() {
        return dateTimeSlot;
    }

    public void setDateTimeSlot(DateTimeSlot dateTimeSlot) {
        this.dateTimeSlot = dateTimeSlot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(table, flags);
        dest.writeParcelable(dateTimeSlot, flags);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Reservation> CREATOR = new Parcelable.Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    protected Reservation(Parcel in) {
        table = in.readParcelable(Table.class.getClassLoader());
        dateTimeSlot = in.readParcelable(DateTimeSlot.class.getClassLoader());
    }
}
