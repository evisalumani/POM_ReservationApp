package de.tum.pom16.teamtba.reservationapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by evisa on 9/6/16.
 */
public class OpeningTimes implements Parcelable {
    private HourTimeSlot openingTimeSlot;
    private HourTimeSlot closingTimeslot;

    public OpeningTimes(HourTimeSlot openingHour, HourTimeSlot closingHour) {
        this.openingTimeSlot = openingHour;
        this.closingTimeslot = closingHour;
    }

    public HourTimeSlot getOpeningTimeSlot() {
        return openingTimeSlot;
    }

    public void setOpeningTimeSlot(HourTimeSlot openingTimeSlot) {
        this.openingTimeSlot = openingTimeSlot;
    }

    public HourTimeSlot getClosingTimeslot() {
        return closingTimeslot;
    }

    public void setClosingTimeslot(HourTimeSlot closingTimeslot) {
        this.closingTimeslot = closingTimeslot;
    }

    public HourTimeSlot getLastAvailableReservationSlot() {
        return HourTimeSlot.convertFromDecimalRepresentation(HourTimeSlot.convertToDecimalRepresentation(closingTimeslot) - HourTimeSlot.stepFraction);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(openingTimeSlot, flags);
        dest.writeParcelable(closingTimeslot, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OpeningTimes> CREATOR = new Parcelable.Creator<OpeningTimes>() {
        @Override
        public OpeningTimes createFromParcel(Parcel in) {
            return new OpeningTimes(in);
        }

        @Override
        public OpeningTimes[] newArray(int size) {
            return new OpeningTimes[size];
        }
    };

    protected OpeningTimes(Parcel in) {
        openingTimeSlot = in.readParcelable(HourTimeSlot.class.getClassLoader());
        closingTimeslot = in.readParcelable(HourTimeSlot.class.getClassLoader());
    }

    @Override
    public boolean equals(Object another) {
        OpeningTimes otherOpeningTimes = (OpeningTimes) another;
        //two opening times are equal if their opening and closing timeslots are respectively equal
        //return openingTimeSlot.equals(otherOpeningTimes.getOpeningTimeSlot()) && closingTimeslot.equals(otherOpeningTimes.getClosingTimeslot());
        return this.openingTimeSlot.compareTo(otherOpeningTimes.getOpeningTimeSlot()) == 0
                && this.closingTimeslot.compareTo(otherOpeningTimes.getClosingTimeslot()) == 0;
    }

    @Override
    public String toString() {
        return openingTimeSlot.toString() + " - " + closingTimeslot.toString();
    }
}