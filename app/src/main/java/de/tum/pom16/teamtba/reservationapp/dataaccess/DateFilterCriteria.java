package de.tum.pom16.teamtba.reservationapp.dataaccess;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.OpeningTimes;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/21/16.
 */
public class DateFilterCriteria extends FilterCriteria {
    // TODO: implement
    private HourTimeSlot timeSlot;
    public DateFilterCriteria(Object criteria) {
        super(criteria);
    } //criteria is a date

    public HourTimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(HourTimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean filter(Restaurant restaurant) {
        Calendar date = (Calendar)criteria;
        date.get(Calendar.DAY_OF_WEEK); //starting from 1 to 7
        OpeningTimes openingTimesForDayOfWeek = restaurant.getOpeningTimes().get(date.get(Calendar.DAY_OF_WEEK));

        if (timeSlot != null) {

            
        }
        return openingTimesForDayOfWeek != null; //if restaurant is open on that day or not
    }
}
