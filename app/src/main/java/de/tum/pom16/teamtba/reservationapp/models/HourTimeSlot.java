package de.tum.pom16.teamtba.reservationapp.models;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by evisa on 9/7/16.
 */
public class HourTimeSlot implements Comparable {
    private int hour;
    private int minute;
    private static float stepFraction = (float)0.5; //each step-wise division of an hour is by 0.5 (i.e. by 30 min)
    private final static int MIN_IN_HOUR = 60;

    public HourTimeSlot(int hour, int minute) {
        //data validation
        //valid ranges: 0-23 for hour; 0-59 for minute
        //valid minutes, depending on the hour division (e.g. by quarter, by half etc.)
        if (hour >= 0 && hour <= 24 && minute >= 0 && minute < 60 && containsValidMinutes(minute)) {
            this.hour = hour;
            this.minute = minute;
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public static float getStep() {
        return stepFraction;
    }

    public static void setStep(float step) {
        HourTimeSlot.stepFraction = step;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final HourTimeSlot other = (HourTimeSlot) obj;

        if (this.hour != other.hour || this.minute != other.minute) {
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(Object another) {
        HourTimeSlot otherTimeSlot = (HourTimeSlot) another;
        return getHelperCalendarForTimeSlot(this).compareTo(getHelperCalendarForTimeSlot(otherTimeSlot));
    }

    private Calendar getHelperCalendarForTimeSlot(HourTimeSlot timeSlot) {
        Calendar currentTimeSlotCal = Calendar.getInstance();
        currentTimeSlotCal.set(Calendar.HOUR_OF_DAY, timeSlot.hour);
        currentTimeSlotCal.set(Calendar.MINUTE, timeSlot.minute);
        currentTimeSlotCal.set(Calendar.SECOND, 0);
        currentTimeSlotCal.set(Calendar.MILLISECOND, 0);

        return currentTimeSlotCal;
    }

    @Override
    public String toString() {
        return formatTimeElement(hour) + ":" + formatTimeElement(minute);
    }

    public static HourTimeSlot fromString(String str) {
        String parts[] = str.split(":");
        if (parts != null && parts.length == 2) {
            return new HourTimeSlot(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
        }
        return null;
    }

    private boolean containsValidMinutes(int minutes) {
        int[] validMinutes = getValidMinutesNumbers();
        for (int i=0; i<validMinutes.length; i++) {
            if (validMinutes[i] == minutes)  {
                return true;
            }
        }

        return false;
    }

    private int[] getValidMinutesNumbers() {
        //available numbers for minutes
        int nrOfHourDivisions = (int) (1/stepFraction);
        int stepSizeInMinutes = (int)(HourTimeSlot.MIN_IN_HOUR * HourTimeSlot.stepFraction);
        int[] validMinutes = new int[nrOfHourDivisions];
        for (int i=0; i<nrOfHourDivisions; i++) {
            validMinutes[i] = i*stepSizeInMinutes;
        }

        return validMinutes;
    }

    private String formatTimeElement(int timeElement) {
        String str = String.valueOf(timeElement);
        str = str.length() < 2 ? "0" + str : str;
        return str;
    }

    public static List<HourTimeSlot> getAllDailyTimeSlots() {
        List<HourTimeSlot> timeSlots = new ArrayList<HourTimeSlot>();

        //e.g. from 00:00 to 23:30
        for (float i = 0; i<24; i += stepFraction) {
            timeSlots.add(HourTimeSlot.convertFromDecimalRepresentation(i));
        }

        return timeSlots;
    }

    public static List<HourTimeSlot> getAvailableTimeSlotsInBetween(HourTimeSlot openingSlot, HourTimeSlot closingSlot) {
        //openingSlot is before closingSlot
        //suppose these are the opening and closing times of a restaurant

        if (openingSlot.compareTo(closingSlot) < 0) {
            List<HourTimeSlot> availableTimeSlots = new ArrayList<HourTimeSlot>();

            float openingSlotInDecimal = HourTimeSlot.convertToDecimalRepresentation(openingSlot);
            float closingSlotInDecimal = HourTimeSlot.convertToDecimalRepresentation(closingSlot);

            while (openingSlotInDecimal + stepFraction < closingSlotInDecimal) {
                openingSlotInDecimal += stepFraction;
                availableTimeSlots.add(HourTimeSlot.convertFromDecimalRepresentation(openingSlotInDecimal));
            }

            return availableTimeSlots;
        }

        return null;
    }

    public static float convertToDecimalRepresentation(HourTimeSlot timeSlot) {
        //timeSlot.hour is the whole part
        //convert timeSlot.minute to decimal part

        float decimalPart = timeSlot.getMinute()/HourTimeSlot.MIN_IN_HOUR;
        return timeSlot.getHour() + decimalPart;
    }

    public static HourTimeSlot convertFromDecimalRepresentation(float input) {
        float decimalPart = input - (int)input;
        int minutes = (int) (decimalPart*HourTimeSlot.MIN_IN_HOUR);
        return new HourTimeSlot((int)input, minutes);
    }

    public HourTimeSlot addHour(int durationInHours) {
        //duration in hours; 1 hour by default
        return new HourTimeSlot(hour + durationInHours, minute);
    }
}
