package de.tum.pom16.teamtba.reservationapp.models;

import android.content.Intent;
import android.provider.CalendarContract;

import java.util.Calendar;

/**
 * Created by evisa on 7/12/16.
 */
public class CalendarEvent {
    private String restaurantName;
    private int tableNo;
    private int noOfPeople;
    private String title; //Reservation at <restaurant name>
    private String description;
    private String location;
    private int startTime;
    private int endTime;

    public CalendarEvent(int tableNo, int noOfPeople, int startTime, int endTime) {
        this.tableNo = tableNo;
        this.noOfPeople = noOfPeople;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CalendarEvent(String restaurantName, int tableNo, int noOfPeople, String location, int startTime, int endTime) {
        this(tableNo, noOfPeople, startTime, endTime);
        this.restaurantName = restaurantName;
        this.location = location;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return "Reservation at " + getRestaurantName();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public String getDescription() {
        return "Table #" + String.valueOf(tableNo) + " reserved for " + String.valueOf(getNoOfPeople()) + " persons";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Intent createCalendarEventIntent() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, getStartTime());
        cal.set(Calendar.MINUTE, 0);

        Intent calEventIntent = new Intent(Intent.ACTION_INSERT);
        calEventIntent.setType("vnd.android.cursor.item/event");
        calEventIntent.putExtra(CalendarContract.Events.TITLE, getTitle());
        calEventIntent.putExtra(CalendarContract.Events.DESCRIPTION, getDescription());
        calEventIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, getLocation());
        //start time, end time
        calEventIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                cal.getTimeInMillis());
        calEventIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                cal.getTimeInMillis()+60*60*1000); //add one hour (in ms)

        return calEventIntent;
    }
}
