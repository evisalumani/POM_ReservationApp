package de.tum.pom16.teamtba.reservationapp.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.*;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.dataaccess.*;
import de.tum.pom16.teamtba.reservationapp.models.RestaurantType;

/**
 * Created by evisa on 6/5/16.
 */
public class FilterActivity extends AppActivity {
    //view
    Spinner typeSpinner;
    SeekBar distanceSeekbar;
    SeekBar priceSeekbar;
    RatingBar ratingBar;
    Spinner visitorSpinner;
    TextView distanceTextView;
    TextView priceTextView;
    CalendarView calendar;
    TimePicker time;

    //model
    List<Restaurant> restaurants;
    List<FilterCriteria> filterCriteria;

    @Override
    protected void initializeModel() {
        restaurants = DataGenerator.generateDummyData();
        filterCriteria = new ArrayList<FilterCriteria>();
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        setContentView(R.layout.activity_filter);

        initializeTypeFilterUI();
        initializeDistanceFilterUI();
        initializePriceFilterUI();
        initializeRatingFilterUI();
        initializeVisitorNrFilterUI();
        initializeCalendarFilterUI();
        initializeTimeFilterUI();
    }

    private void initializeTypeFilterUI() {
        typeSpinner = (Spinner) findViewById(R.id.filter_type_spinner);
        ArrayAdapter<CharSequence> typeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.restaurant_types, android.R.layout.simple_spinner_item);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeArrayAdapter);
        typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String restaurantType = (String) parent.getItemAtPosition(position);
                if (!restaurantType.equalsIgnoreCase("all") && filterCriteria != null) {
                    TypeFilterCriteria typeCriteria = new TypeFilterCriteria(RestaurantType.valueOf(restaurantType.toUpperCase()));
                    filterCriteria.add(typeCriteria);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeDistanceFilterUI() {
        distanceTextView = (TextView) findViewById(R.id.filter_distance_textview);

        distanceSeekbar = (SeekBar) findViewById(R.id.filter_distance_seekbar);
        distanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float distance = (float) progress;
                if (distance != 0 && filterCriteria != null ) {
                    // TODO: add user location
                    distanceTextView.setText("DISTANCE "+ distance + " km");
                    DistanceFilterCriteria distanceCriteria = new DistanceFilterCriteria(distance, null);
                    filterCriteria.add(distanceCriteria);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void initializePriceFilterUI() {
        priceTextView = (TextView) findViewById(R.id.filter_price_textview);

        priceSeekbar = (SeekBar) findViewById(R.id.filter_price_seekbar);
        priceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double price = (double) progress;
                if (price != 0 && filterCriteria != null ) {
                    // TODO: add user location
                    priceTextView.setText("PRICE AT MOST " + price + " Euro");
                    PriceFilterCriteria priceCriteria = new PriceFilterCriteria(price);
                    filterCriteria.add(priceCriteria);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void initializeRatingFilterUI() {
        ratingBar = (RatingBar) findViewById(R.id.filter_rating_ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating != 0 && filterCriteria != null) {
                    RatingFilterCriteria ratingCriteria = new RatingFilterCriteria(rating);
                    filterCriteria.add(ratingCriteria);
                }
            }
        });

    }

    private void initializeVisitorNrFilterUI() {
        visitorSpinner = (Spinner) findViewById(R.id.filter_visitors_spinner);
        ArrayAdapter<CharSequence> visitorArrayAdapter = ArrayAdapter.createFromResource(this, R.array.number_of_visitors, android.R.layout.simple_spinner_item);
        visitorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitorSpinner.setAdapter(visitorArrayAdapter);
        visitorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int numberOfVisitors = Integer.parseInt((String) parent.getItemAtPosition(position));
                if (filterCriteria != null && numberOfVisitors != 0) {
                    // TODO: implement filter for number of visitors
                    // TODO: for demo, each table at least 4 people
                    VisitorsNumberFilterCriteria visitorsNrCriteria = new VisitorsNumberFilterCriteria(numberOfVisitors);
                    filterCriteria.add(visitorsNrCriteria);
                    System.out.println(numberOfVisitors);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initializeCalendarFilterUI() {
        calendar = (CalendarView) findViewById(R.id.filter_calendarview);
        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(2); //Monday as first day in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // TODO: implement filter for date
                System.out.println(year + " " + month + " " + dayOfMonth);
            }
        });
    }

    private void initializeTimeFilterUI() {
        time = (TimePicker) findViewById(R.id.filter_timepicker);
        time.setIs24HourView(true);
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // TODO: implement filter for time
                ReservationTimeFilterCriteria timeCriteria = new ReservationTimeFilterCriteria(hourOfDay);
                filterCriteria.add(timeCriteria);
                System.out.println(hourOfDay + ":" + minute);
            }
        });
    }

    public void cancelFilters(View view) {
        // TODO: check if better way of going back to previous activity
        finish(); //finish activity
    }

    public void applyFilters(View view) {
        if (restaurants != null && filterCriteria != null) {
            restaurants = DataSearch.filter(restaurants, filterCriteria);
            // TODO: start intent and pass filtered restaurants
            returnToSearchResults(restaurants);
        }
    }


    private void returnToSearchResults(List<Restaurant> restaurants) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putParcelableArrayListExtra(IntentType.INTENT_FILTER_TO_SEARCH_RESULTS.name(), (ArrayList<? extends Parcelable>) restaurants);
        startActivity(intent);
    }
}
