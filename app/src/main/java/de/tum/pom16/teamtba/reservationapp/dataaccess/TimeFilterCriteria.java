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
        if (timeSlot == null) {
            //not interested in the timeslot, just if the restaurant is open on this day
            return restaurant.isOpenAtDayOfWeek(date);
        } else if (restaurant.isOpenAtTimeSlot(date, timeSlot)) {
            DateTimeSlot dateTimeSlot = new DateTimeSlot(date, timeSlot);
            // check if there exists any table, for the given restaurant,
            // which contains an available reservation for the given date and time slot
            //TODO: get() or contains()
            //TODO: remove x, just return directly
            boolean x= Stream.of(restaurant.getTables()).anyMatch(table ->
                            //either there are no reservations for the table, or there are no reservations at the given time slot
                            table.getReservations() == null || table.getReservations().size() == 0 || Stream.of(table.getReservations()).noneMatch(reservation -> reservation.getDateTimeSlot() == dateTimeSlot)
                    //return table.getTableReservationStatus() != null && table.getTableReservationStatus().size() > 0 && table.getTableReservationStatus().get(dateTimeSlot) == true;
            );
            return x;
        } else {
            return false;
        }
    }
}
