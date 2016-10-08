package de.tum.pom16.teamtba.reservationapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.utilities.AlphaNumericTextValidator;
import de.tum.pom16.teamtba.reservationapp.utilities.EmailValidator;
import de.tum.pom16.teamtba.reservationapp.utilities.PersonNameValidator;
import de.tum.pom16.teamtba.reservationapp.utilities.PhoneValidator;

public class ReservationDetailsActivity extends AppActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText specialRequestsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionBar();

        setupReservationForm();
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
