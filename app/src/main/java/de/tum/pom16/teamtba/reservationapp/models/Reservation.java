package de.tum.pom16.teamtba.reservationapp.models;

/**
 * Created by evisa on 9/27/16.
 */
public class Reservation {
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
}
