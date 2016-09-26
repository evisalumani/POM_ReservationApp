package de.tum.pom16.teamtba.reservationapp.dataaccess;

import com.annimon.stream.Stream;

import java.util.Calendar;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.DateTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.OpeningTimes;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.Table;

/**
 * Created by evisa on 9/21/16.
 */
public class TimeFilterCriteria extends FilterCriteria {
    // TODO: implement
    private HourTimeSlot timeSlot;
    public TimeFilterCriteria(Object criteria) {
        super(criteria);
    } //criteria is a Calendar

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

        if (timeSlot != null && restaurant.getTables() != null && restaurant.getTables().size() > 0) {
            DateTimeSlot dateTimeSlot = new DateTimeSlot(date, timeSlot);
            // check if there exists any table, for the given restaurant,
            // which contains an available reservation for the given date and time slot
            //TODO: get() or contains()
            return Stream.of(restaurant.getTables()).anyMatch(table -> {
                //false - bcs there is no reservation for that time slot
                return table.getTableReservationStatus() != null && table.getTableReservationStatus().size() > 0 && table.getTableReservationStatus().get(dateTimeSlot) == true;
                });
            //.filter(table -> table.getTableReservationStatus().contains(dateTimeSlot));
        }

        //no timeslot criteria, only day
        return openingTimesForDayOfWeek != null; //if restaurant is open on that day or not
    }
}
