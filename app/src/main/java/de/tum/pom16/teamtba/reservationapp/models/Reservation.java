package de.tum.pom16.teamtba.reservationapp.models;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by evisa on 9/27/16.
 */
public class Reservation implements Parcelable {
    private Table table; //table links to restaurant
    private DateTimeSlot dateTimeSlot;
    private String specialRequests;
    private UserContact userContact;

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

    public void setSpecialRequests(String specialRequirements) {
        this.specialRequests = specialRequirements;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
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

    //needed in case a calendar event is added to the device's calendar
    //the intent to be started in an activity: startActivity(intent)
    public Intent createCalendarEventIntent() {
        Calendar startTime = DateTimeSlot.getCalendarFromDateTimeSlot(dateTimeSlot, true);
        Calendar endTime = DateTimeSlot.getCalendarFromDateTimeSlot(dateTimeSlot, false);

        Intent calEventIntent = new Intent(Intent.ACTION_INSERT);
        calEventIntent.setType("vnd.android.cursor.item/event");
        calEventIntent.putExtra(CalendarContract.Events.TITLE, "Reservation at " + table.getRestaurantName());
        calEventIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Table for " + table.getCapacity());
        calEventIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, table.getRestaurantAddress());
        //start time, end time
        calEventIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                startTime.getTimeInMillis());
        calEventIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        //or startTime.getTimeInMillis()+60*60*1000; to add one hour (in ms)

        return calEventIntent;
    }
}
