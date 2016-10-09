package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.annimon.stream.Collector;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.Frame;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.NoSearchResultsFragment;
import de.tum.pom16.teamtba.reservationapp.customviews.SearchResultsAdapter;
import de.tum.pom16.teamtba.reservationapp.customviews.SearchResultsFragment;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataGenerator;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataSearch;
import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;
import de.tum.pom16.teamtba.reservationapp.dataaccess.SortByDistance;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.utilities.*;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SearchResultsActivity extends MapCallbackActivity {
    //view
    ListView searchResultsListView;
    ListAdapter searchResultsAdapter;
    SupportMapFragment mapFragment;
    FrameLayout frameContainer;

    //model
    GlobalSearchFilters filters;
    List<Restaurant> searchResults;
    LocationUtility locationUtility;
    private static Observer<List<Restaurant>> oberserverOnRestaurants;

    @Override
    protected void initializeModel() {
        super.initializeModel();
        filters = GlobalSearchFilters.getSharedInstance();

        oberserverOnRestaurants = new Observer<List<Restaurant>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<Restaurant> restaurants) {
                searchResults = restaurants;
                if (restaurants == null || restaurants.size() == 0) {
                    Toast.makeText(SearchResultsActivity.this, "No Results", Toast.LENGTH_SHORT).show();

                    NoSearchResultsFragment noResultsFragment = new NoSearchResultsFragment();
                    //TODO: check if container is not null
                    // Add the fragment to the container FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.searchResults_frame_container, noResultsFragment).commit();

                } else {
                    //there are search results ->
                    SearchResultsFragment resultsFragment = new SearchResultsFragment();
                    //TODO: check if container is not null
                    // Add the fragment to the container FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.searchResults_frame_container, resultsFragment).commit();


                    //get map
//                    mapFragment.getMapAsync(SearchResultsActivity.this);
//                    addMarkersForSearchResults(); //TODO: can also pass restaurants here
//                    setupListview();
                }
            }
        };

        //filtering to be performed on activity start, e.g. when coming back from Filter activity
        if (filters.getFilterCriteria().size() >= 2 && filters.getDataSort() != null) { //i.e. at least a date and a location filter
            searchResults = (ArrayList) filters.applyFilters(); //there is at least the filter of date (dd.mm.yyy) and location
            filters.getDataSort().setRestaurants(searchResults);
            searchResults = filters.getDataSort().sort();

            //rx java
            Observable.just(searchResults)
                    //concurrency: observe what happens on main thread; react to it on a separate thread
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(oberserverOnRestaurants);
        }
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameContainer = (FrameLayout)findViewById(R.id.searchResults_frame_container);

        //mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //TODO: call this later
        //mapFragment.getMapAsync(this);
    }

    public static Observer<List<Restaurant>> getOberserverOnRestaurants() {
        return oberserverOnRestaurants;
    }

//    private void setupListview() {
//        //use helper method so that: searchResults and the data bound to the array adapter point to different memory locations
//        //needed for the search functionality (search icon on action bar), so that we keep track of the initial searchResults,
//        //independent of the queries issued on the action bar
//        searchResultsAdapter = new SearchResultsAdapter(SearchResultsActivity.this, Helpers.deepCopyRestaurants(searchResults));
//        searchResultsListView = (ListView) findViewById(R.id.searchResults_listview);
//        searchResultsListView.setAdapter(searchResultsAdapter);
//
//        //Handle item click from list view
//        searchResultsListView.setClickable(true);
//        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                Restaurant restaurant = (Restaurant) searchResultsListView.getItemAtPosition(position);
//                goToRestaurantDetails(restaurant);
//            }
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if (filters.getCurrentUserLocation() == null)
            locationUtility.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationUtility.isConnected()) {
            locationUtility.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationUtility.removeLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LocationUtility.REQUEST_LOCATION) {
            //length == 1 before
            //set isLocationPermitted boolean value, depending on whether the permission was granted or denied from the user via the dialog
            boolean isPermissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[3] == PackageManager.PERMISSION_GRANTED;
            locationUtility.setLocationPermitted(isPermissionGranted);
            locationUtility.onReceivingLocationPermission(isPermissionGranted);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case LocationUtility.REQUEST_LOCATION_SETTING:
                //Activity_RESULT_OK, Activity_RESULT_CANCELED
                boolean isGpsEnabled = resultCode == Activity.RESULT_OK;
                locationUtility.setGPSEnabled(isGpsEnabled);
                locationUtility.onReceivingGpsPermission(isGpsEnabled);
                break;
        }
    }

    public void addMarkersForSearchResults() {
        if (searchResults != null) {
            for (Restaurant restaurant : searchResults) {
                addMarker(restaurant.getLatitude(), restaurant.getLongitude(), restaurant.getName());
            }
        }
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);

        //configure search
        SearchUtility searchUtility = new SearchUtility(SearchResultsActivity.this, searchMenuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_hockeyapp_feedback:
                showFeedbackActivity();
                break;
            case R.id.menu_filter:
                showFilterActivity();
                break;
            default:
                break;
        }

        return true;
    }

    public void showFilterActivity() {
        Intent intent = new Intent(this, FilterResultsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());

        locationUtility = new LocationUtility(this);
    }


    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            Toast.makeText(this, "Query: " + query, Toast.LENGTH_SHORT).show();
            //doSearch(query)
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (filters.getCurrentUserLocation() == null)
            locationUtility.connect();
    }

    public void onNewQueryTerm(String newText) {
        List<Restaurant> tempSearchResults = DataSearch.filterRestaurantContainingString(searchResults, newText);
        ((SearchResultsAdapter) searchResultsAdapter).refreshRestaurants(tempSearchResults);
    }
}