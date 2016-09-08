package de.tum.pom16.teamtba.reservationapp.models;


import java.util.Date;

/**
 * Created by evisa on 9/6/16.
 */
public class DateTimeSlot {
    private Date date;
    private HourTimeSlot startTime;
    private HourTimeSlot endTime;
    private static int duration = 1; // in hours (1 by default); used for reservations

    public DateTimeSlot(Date date, HourTimeSlot startTime) {
        this.date = date;
        this.startTime = startTime;
        setEndTime();
    }

    public void setEndTime() {
        if (startTime != null) {
            endTime = startTime.addHour(DateTimeSlot.duration);
        }
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
}
