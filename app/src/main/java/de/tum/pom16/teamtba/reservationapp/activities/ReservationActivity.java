package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.TableReservationAdapter;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataSearch;
import de.tum.pom16.teamtba.reservationapp.models.CalendarEvent;
import de.tum.pom16.teamtba.reservationapp.models.Table;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by gani on 23/06/2016.
 */
public class ReservationActivity extends AppActivity {

    //UI
    ListView tablesListView;
    TextView restaurantName;
    Spinner timeSlotSpinner;
    ListAdapter reservationAdapter;

    //model
    List<Table> filteredTables;
    Restaurant restaurant;
    int selectedTimeSlot;

    @Override
    protected void initializeModel() {
        super.initializeModel();
        Intent mIntent = getIntent();
        restaurant = (Restaurant) mIntent.getParcelableExtra(IntentType.INTENT_DETAILS_TO_RESERVATION.name());
    }

    @Override
    protected void initializeView() {
        super.initializeView();

        setContentView(R.layout.activity_reservation);

        if (restaurant != null) {

            //by default: available tables of 1st dataslot at displayed
            filteredTables = DataSearch.filterTablesByTimeSlot(restaurant, 0);

            //setup UI
            restaurantName = (TextView) findViewById(R.id.reservation_restaurantName);
            restaurantName.setText(restaurant.getName() + "\nOpening Hours: " + String.valueOf(restaurant.getOpeningHour()) +  " - " + String.valueOf(restaurant.getClosingHour()));

            //table listview
            reservationAdapter = new TableReservationAdapter(this, filteredTables);
            tablesListView = (ListView) findViewById(R.id.reservation_filteredTables_listview);
            tablesListView.setAdapter(reservationAdapter);

            //timeslot spinner
            timeSlotSpinner = (Spinner) findViewById(R.id.reservation_slot_spinner);
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, restaurant.getTimeSlots());
            timeSlotSpinner.setAdapter(spinnerArrayAdapter);
            timeSlotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedTimeSlot = position;
                    //filter by timeslot
                    filteredTables = DataSearch.filterTablesByTimeSlot(restaurant, position);

                    //reload available tables
                    ((TableReservationAdapter) reservationAdapter).refreshTables(filteredTables);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Handle item click from list view
            tablesListView.setClickable(true);
            tablesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Table table = (Table) tablesListView.getItemAtPosition(position);
                    createAlertDialog(table);
                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        filteredTables = DataSearch.filterTablesByTimeSlot(restaurant, selectedTimeSlot);
        //reload available tables
        ((TableReservationAdapter) reservationAdapter).refreshTables(filteredTables);
    }

    public void createAlertDialog(Table table) {
        new AlertDialog.Builder(this)
                .setTitle("Complete Reservation")
                .setMessage("Do you want to reserve this table?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //create new CalendarEvent
                        int timeHourStart = (int)timeSlotSpinner.getSelectedItem();
                        int timeHourEnd = timeHourStart + 1;

                        //make reservation
                        try {
                            CalendarEvent calEvent = table.setReservation(timeSlotSpinner.getSelectedItemPosition());
                            if (calEvent != null) {
                                //add other properties
                                calEvent.setRestaurantName(restaurant.getName());
                                calEvent.setLocation(restaurant.getAddress());
                                calEvent.setStartTime(timeHourStart);
                                calEvent.setEndTime(timeHourEnd);

                                //create intent
                                Intent calEventIntent = calEvent.createCalendarEventIntent();
                                //start activity
                                startActivity(calEventIntent);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}