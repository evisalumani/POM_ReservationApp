package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.FragmentTransaction;
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
import java.util.Calendar;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.DateDialogFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.TimeSlotDialogFragment;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.dataaccess.*;
import de.tum.pom16.teamtba.reservationapp.models.CuisineType;

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
    TextView selectedDateTextView;
    CalendarView calendar;
    TimePicker time;

    //model
    List<Restaurant> restaurants;
    //List<FilterCriteria> filterCriteria;

    @Override
    protected void initializeModel() {
        restaurants = DataGenerator.generateDummyData();
        //filterCriteria = new ArrayList<FilterCriteria>();
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
                if (!restaurantType.equalsIgnoreCase("all")) {
                    TypeFilterCriteria typeCriteria = new TypeFilterCriteria(CuisineType.valueOf(restaurantType.toUpperCase()));
                    //filterCriteria.add(typeCriteria);
                    GlobalSearchFilters.getSharedInstance().addSearchCriteria(SearchFilterType.CUISINE_TYPE, typeCriteria);
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
                if (distance != 0) {
                    // TODO: add user location
                    distanceTextView.setText("DISTANCE "+ distance + " km");
                    DistanceFilterCriteria distanceCriteria = new DistanceFilterCriteria(distance, null);
                    //filterCriteria.add(distanceCriteria);
                    GlobalSearchFilters.getSharedInstance().addSearchCriteria(SearchFilterType.LOCATION, distanceCriteria);
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
                if (price != 0) {
                    // TODO: add user location
                    priceTextView.setText("PRICE AT MOST " + price + " Euro");
                    PriceFilterCriteria priceCriteria = new PriceFilterCriteria(price);
                    //filterCriteria.add(priceCriteria);
                    GlobalSearchFilters.getSharedInstance().addSearchCriteria(SearchFilterType.PRICE_CATEGORY, priceCriteria);
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
                if (rating != 0) {
                    RatingFilterCriteria ratingCriteria = new RatingFilterCriteria(rating);
                    //filterCriteria.add(ratingCriteria);
                    GlobalSearchFilters.getSharedInstance().addSearchCriteria(SearchFilterType.RATINGS, ratingCriteria);
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
                if (numberOfVisitors != 0) {
                    // TODO: implement filter for number of visitors
                    // TODO: for demo, each table at least 4 people
                    VisitorsNumberFilterCriteria visitorsNrCriteria = new VisitorsNumberFilterCriteria(numberOfVisitors);
                    //filterCriteria.add(visitorsNrCriteria);
                    System.out.println(numberOfVisitors);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

//    private void initializeCalendarFilterUI() {
//        calendar = (CalendarView) findViewById(R.id.filter_calendarview);
//        calendar.setShowWeekNumber(false);
//        calendar.setFirstDayOfWeek(2); //Monday as first day in calendar
//        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                // TODO: implement filter for date
//
//                System.out.println(year + " " + month + " " + dayOfMonth);
//            }
//        });
//    }

    private void initializeCalendarFilterUI() {
        selectedDateTextView = (TextView) findViewById(R.id.filter_selectedDate_textview);

        GlobalSearchFilters.getSharedInstance().setDate(Calendar.getInstance()); //date is set to "today"
        selectedDateTextView.setText(GlobalSearchFilters.getSharedInstance().getDateString());

        selectedDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialogFragment dateDialog = new DateDialogFragment(view, GlobalSearchFilters.getSharedInstance().getDate());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dateDialog.show(fragmentTransaction, "DatePicker");
            }
        });
    }

    private void initializeTimeFilterUI() {
        TextView timeTextView = (TextView) findViewById(R.id.filter_selectedTime_textview);

//        timeTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimeSlotDialogFragment timeDialog = new TimeSlotDialogFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                timeDialog.show(fragmentTransaction, "TimePicker");
//            }
//        });
//        String[] allTimeSlots
//        np.setDisplayedValues();
//        time.setIs24HourView(true);
//        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                // TODO: implement filter for time
//                ReservationTimeFilterCriteria timeCriteria = new ReservationTimeFilterCriteria(hourOfDay);
//                //filterCriteria.add(timeCriteria);
//
//                System.out.println(hourOfDay + ":" + minute);
//            }
//        });
    }

    public void cancelFilters(View view) {
        // TODO: check if better way of going back to previous activity
        finish(); //finish activity
    }

    public void applyFilters(View view) {
        if (restaurants != null) {
            //restaurants = DataSearch.filter(restaurants, filterCriteria);
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
