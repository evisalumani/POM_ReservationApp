package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.CuisineDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.DateDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.PriceDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.SortByDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.TimeSlotDialogFragment;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

public class FilterResultsActivity extends AppActivity {
    private GlobalSearchFilters filters;

    //UI
    TextView cuisineTextView;
    TextView locationTextView;
    TextView priceTextView;
    TextView ratingTextView;
    TextView dateTextView;
    TextView timeTextView;
    TextView sortByTextView;

    @Override
    protected void initializeView() {
        setContentView(R.layout.activity_filter_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up saved filters
        filters = GlobalSearchFilters.getSharedInstance();

        //initialize UI elements
        cuisineTextView = (TextView) findViewById(R.id.filter_cuisine_textview);
        locationTextView = (TextView) findViewById(R.id.filter_location_textview);
        priceTextView = (TextView) findViewById(R.id.filter_price_textview);
        ratingTextView = (TextView) findViewById(R.id.filter_rating_textview);
        dateTextView = (TextView) findViewById(R.id.filter_date_textview);
        timeTextView = (TextView) findViewById(R.id.filter_time_textview);
        sortByTextView = (TextView) findViewById(R.id.filter_sort_textview);

        setupSavedFilters();
        setupUIListeners();
    }

    private void setupSavedFilters() {
        //date is set to today by default
        filters.setDate(Calendar.getInstance());

        dateTextView.setText(filters.getDateString());

    }

    private void setupUIListeners() {
        cuisineTextView.setOnClickListener(getCuisineClickListener());

        //location
        priceTextView.setOnClickListener(getPriceClickListener());

        dateTextView.setOnClickListener(getDateClickListener());
        timeTextView.setOnClickListener(getTimeClickListener());

        sortByTextView.setOnClickListener(getSortByClickListener());
    }

    private View.OnClickListener getSortByClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortByDialogFragment dateDialog = new SortByDialogFragment("Sort By");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "SortByDialog");
            }
        };
    }

    private View.OnClickListener getPriceClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceDialogFragment dateDialog = new PriceDialogFragment("Max Price");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "PriceDialog");
            }
        };
    }

    private View.OnClickListener getDateClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialogFragment dateDialog = new DateDialogFragment(view, filters.getDate());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "DatePicker");
            }
        };
    }

    private View.OnClickListener getCuisineClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CuisineDialogFragment dateDialog = new CuisineDialogFragment("Cuisine");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "CuisineDialog");
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //go back
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(this, SearchResultsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private View.OnClickListener getTimeClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSlotDialogFragment timeDialog = new TimeSlotDialogFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                timeDialog.show(fragmentTransaction, "TimeSlotPicker");
            }
        };
    }
}
