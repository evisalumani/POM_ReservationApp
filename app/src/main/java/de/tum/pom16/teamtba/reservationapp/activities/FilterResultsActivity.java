package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.Hashtable;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.CuisineDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.DateDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.PriceDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.SortByDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.TimeSlotDialogFragment;
import de.tum.pom16.teamtba.reservationapp.dataaccess.FilterCriteria;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.dataaccess.SearchFilterType;
import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;
import de.tum.pom16.teamtba.reservationapp.models.HourTimeSlot;

public class FilterResultsActivity extends AppActivity {
    private GlobalSearchFilters filters;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

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

        setupUIForSavedFilters();
        setupUIListeners();
    }

    private void setupUIForSavedFilters() {
        //date is set to today by default
        int nrOfSpecificCuisinesSelected = filters.getNrOfSpecificCuisinesSelected();
        cuisineTextView.setText("Cuisines: (" + (nrOfSpecificCuisinesSelected == 0 ? "All" : String.valueOf(nrOfSpecificCuisinesSelected)) + ")");

        int priceCategory = filters.getMaxPriceCategory();
        priceTextView.setText("Price (max): " + Constants.getPriceCategoryStrings()[priceCategory]);

        dateTextView.setText(filters.getDateString());

        HourTimeSlot timeSlot = filters.getTimeSlot();
        timeTextView.setText(timeSlot == null ? "Any Time" : timeSlot.toString());

        int sortBy = filters.getPropertyToSortBy();
        sortByTextView.setText("Sort by: " + Constants.getSortByStrings()[sortBy]);
    }

    private void setupUIListeners() {
        cuisineTextView.setOnClickListener(getCuisineClickListener());
        //TODO: add location listener
        locationTextView.setOnClickListener(getLocationClickListener());
        priceTextView.setOnClickListener(getPriceClickListener());
        dateTextView.setOnClickListener(getDateClickListener());
        timeTextView.setOnClickListener(getTimeClickListener());
        sortByTextView.setOnClickListener(getSortByClickListener());
    }

    private View.OnClickListener getLocationClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(FilterResultsActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        };
    }

    private View.OnClickListener getSortByClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortByDialogFragment dateDialog = new SortByDialogFragment(sortByTextView, "Sort By");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "SortByDialog");
            }
        };
    }

    private View.OnClickListener getPriceClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceDialogFragment dateDialog = new PriceDialogFragment(priceTextView, "Max Price");
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
                CuisineDialogFragment dateDialog = new CuisineDialogFragment(cuisineTextView, "Cuisine");
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
                TimeSlotDialogFragment timeDialog = new TimeSlotDialogFragment(timeTextView, "Pick time slot");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                timeDialog.show(fragmentTransaction, "TimeSlotPicker");
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                //Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                //Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
