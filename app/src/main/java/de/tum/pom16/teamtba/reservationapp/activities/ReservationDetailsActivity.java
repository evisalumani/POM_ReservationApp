package de.tum.pom16.teamtba.reservationapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import de.tum.pom16.teamtba.reservationapp.R;

public class ReservationDetailsActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionBar();
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
