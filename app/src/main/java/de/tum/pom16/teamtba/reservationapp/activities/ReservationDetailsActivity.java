package de.tum.pom16.teamtba.reservationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.DateTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.Reservation;
import de.tum.pom16.teamtba.reservationapp.models.Table;
import de.tum.pom16.teamtba.reservationapp.utilities.AlphaNumericTextValidator;
import de.tum.pom16.teamtba.reservationapp.utilities.EmailValidator;
import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;
import de.tum.pom16.teamtba.reservationapp.utilities.PersonNameValidator;
import de.tum.pom16.teamtba.reservationapp.utilities.PhoneValidator;

public class ReservationDetailsActivity extends AppActivity {
    //reservation details
    private TextView monthTextView;
    private TextView dateTextView;
    private TextView dayOfWeekTextView;
    private TextView restaurantNameTextView;
    private TextView tableInfoTextView;
    private TextView timeTextView;

    //reservation form
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText specialRequestsEditText;

    //model
    private Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();


        Intent mIntent = getIntent();
        reservation = (Reservation) mIntent.getParcelableExtra(Constants.RESERVATION_DETAILS);

        setupReservationDetails();
        setupReservationForm();
    }

    private void setupReservationDetails() {
        if (reservation == null) return;

        monthTextView = (TextView)findViewById(R.id.reservation_month_textview);
        dateTextView = (TextView)findViewById(R.id.reservation_date_textview);
        dayOfWeekTextView = (TextView)findViewById(R.id.reservation_dayOfWeek_textview);
        restaurantNameTextView = (TextView)findViewById(R.id.reservation_restaurantName_textview);
        tableInfoTextView = (TextView)findViewById(R.id.reservation_nrOfPeople_textview);
        timeTextView = (TextView)findViewById(R.id.reservation_time_textview);

        DateTimeSlot dateTimeSlot = reservation.getDateTimeSlot();
        Table table = reservation.getTable();
        if (dateTimeSlot == null || table == null) return;

        monthTextView.setText(Helpers.getMonthString(dateTimeSlot.getDate()).toUpperCase());
        dateTextView.setText(String.valueOf(Helpers.getDate(dateTimeSlot.getDate())));
        dayOfWeekTextView.setText(Helpers.getDayOfWeekString(dateTimeSlot.getDate()));
        tableInfoTextView.setText("Table for " + table.getCapacity() + " person" + (table.getCapacity() == 1 ? "" : "s"));
        timeTextView.setText(reservation.getDateTimeSlot().getStartTime().toString());
    }

    private void setupReservationForm() {
        //get views from xml
        firstNameEditText = (EditText)findViewById(R.id.reservation_details_firstName);
        lastNameEditText = (EditText)findViewById(R.id.reservation_details_lastName);
        emailEditText = (EditText)findViewById(R.id.reservation_details_email);
        phoneEditText = (EditText)findViewById(R.id.reservation_details_phone);
        specialRequestsEditText = (EditText)findViewById(R.id.reservation_details_specialRequests);

        //add textwatchers for validation
        firstNameEditText.addTextChangedListener(new PersonNameValidator(firstNameEditText, true));
        lastNameEditText.addTextChangedListener(new PersonNameValidator(lastNameEditText, true));
        emailEditText.addTextChangedListener(new EmailValidator(emailEditText, true));
        phoneEditText.addTextChangedListener(new PhoneValidator(phoneEditText, true));
        specialRequestsEditText.addTextChangedListener(new AlphaNumericTextValidator(specialRequestsEditText, false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //go back
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //TODO: figure out where to return
            Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}