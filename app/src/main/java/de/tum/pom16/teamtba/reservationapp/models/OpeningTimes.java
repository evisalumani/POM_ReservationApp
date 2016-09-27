package de.tum.pom16.teamtba.reservationapp.models;

/**
 * Created by evisa on 9/6/16.
 */
public class OpeningTimes {
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
}

