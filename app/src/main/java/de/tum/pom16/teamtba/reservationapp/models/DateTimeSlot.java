package de.tum.pom16.teamtba.reservationapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by evisa on 9/6/16.
 */
public class DateTimeSlot implements Parcelable {
    private Calendar date;
    private HourTimeSlot startTime;
    private HourTimeSlot endTime;
    private static int duration = 1; // in hours (1 by default); used for reservations

    public DateTimeSlot(Calendar date, HourTimeSlot startTime) {
        this.date = date;
        this.startTime = startTime;
        setEndTime();
    }

    public void setEndTime() {
        if (startTime != null) {
            //TODO: find a better way to add hours, keep date in mind, e.g. 23:30 + 1 hr = 00:30 instead of 24:30
            endTime = startTime.addHour(DateTimeSlot.duration);
        }
    }

    public static Calendar getCalendarFromDateTimeSlot(DateTimeSlot dateTimeSlot, boolean isStartTime) {
        Calendar cal = dateTimeSlot.getDate();
        HourTimeSlot timeSlot = isStartTime ? dateTimeSlot.getStartTime() : dateTimeSlot.getEndTime();
        cal.set(Calendar.HOUR_OF_DAY, timeSlot.getHour());
        cal.set(Calendar.MINUTE, timeSlot.getMinute());
        return cal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final DateTimeSlot other = (DateTimeSlot) obj;

        if ((this.date == null) ? (other.date != null) : !this.date.equals(other.date)) {
            return false;
        }

        if ((this.startTime == null) ? (other.startTime != null) : !this.startTime.equals(other.startTime)) {
            return false;
        }

        if ((this.endTime == null) ? (other.endTime != null) : !this.endTime.equals(other.endTime)) {
            return false;
        }

        return true;
    }

    public Calendar getDate() {
        return date;
    }

    public HourTimeSlot getStartTime() {
        return startTime;
    }

    public HourTimeSlot getEndTime() {
        return endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //TODO: is writeSerializable to most efficient way to parcel Calendar?
        dest.writeSerializable(date);
        dest.writeParcelable(startTime, flags);
        dest.writeParcelable(endTime, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DateTimeSlot> CREATOR = new Parcelable.Creator<DateTimeSlot>() {
        @Override
        public DateTimeSlot createFromParcel(Parcel in) {
            return new DateTimeSlot(in);
        }

        @Override
        public DateTimeSlot[] newArray(int size) {
            return new DateTimeSlot[size];
        }
    };

    protected DateTimeSlot(Parcel in) {
        date = (Calendar)in.readSerializable();
        startTime = in.readParcelable(HourTimeSlot.class.getClassLoader());
        endTime = in.readParcelable(HourTimeSlot.class.getClassLoader());
    }
}
