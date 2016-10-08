package de.tum.pom16.teamtba.reservationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.DateTimeSlot;
import de.tum.pom16.teamtba.reservationapp.models.Reservation;
import de.tum.pom16.teamtba.reservationapp.models.Table;
import de.tum.pom16.teamtba.reservationapp.models.UserContact;
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
    private Button reserveButton;

    //model
    private Reservation reservation;
    private Table table;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialRequests;

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
        table = reservation.getTable();
        if (dateTimeSlot == null || table == null) return;

        monthTextView.setText(Helpers.getMonthString(dateTimeSlot.getDate()).toUpperCase());
        dateTextView.setText(String.valueOf(Helpers.getDate(dateTimeSlot.getDate())));
        dayOfWeekTextView.setText(Helpers.getDayOfWeekString(dateTimeSlot.getDate()));
        restaurantNameTextView.setText(table.getRestaurantName());
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
        reserveButton = (Button)findViewById(R.id.reservation_details_reserve_button);
        setReserveButtonEnabled();

        //add textwatchers for validation
        firstNameEditText.addTextChangedListener(new PersonNameValidator(this, firstNameEditText, true));
        lastNameEditText.addTextChangedListener(new PersonNameValidator(this, lastNameEditText, true));
        emailEditText.addTextChangedListener(new EmailValidator(this, emailEditText, true));
        phoneEditText.addTextChangedListener(new PhoneValidator(this, phoneEditText, true));
        specialRequestsEditText.addTextChangedListener(new AlphaNumericTextValidator(this, specialRequestsEditText, false));

        //add button click listener
        reserveButton.setOnClickListener(getReserveButtonClickListener());
    }

    private View.OnClickListener getReserveButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (specialRequests!=null) reservation.setSpecialRequests(specialRequests);
                reservation.setUserContact(new UserContact(firstName, lastName, email, phone));

                //update table's reservations
                table.addReservation(reservation);

                Toast.makeText(getApplicationContext(), "Reservation successful", Toast.LENGTH_SHORT).show();

                //go to search results
                returnToSearchResults();
            }
        };
    }

    public void setEmail(String email) {
        this.email = email;
        setReserveButtonEnabled();
    }

    public void setPhone(String phone) {
        this.phone = phone;
        setReserveButtonEnabled();
    }

    public void setSpecialRequests(String specialRequests) {
        //No need to call setReserveButtonEnabled(); because this field is optional
        this.specialRequests = specialRequests;
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

    public void setName(EditText editText, String input) {
        if (editText == firstNameEditText) firstName = input;
        if (editText == lastNameEditText) lastName = input; //edit text could be for first or last name
        setReserveButtonEnabled();
    }

    private void setReserveButtonEnabled() {
        reserveButton.setEnabled(firstName != null && lastName != null && email != null && phone != null);
    }
}