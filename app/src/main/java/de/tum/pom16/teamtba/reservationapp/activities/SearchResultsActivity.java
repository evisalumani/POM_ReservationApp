package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.Activity;
import android.app.SearchManager;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.SearchResultsAdapter;
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

    private static final int ZOOM_LEVEL = 15;

    //model
    GlobalSearchFilters filters;
    List<Restaurant> searchResults;
    LocationUtility locationUtility;
    private static Observer<List<Restaurant>> oberserverOnRestaurants;

    SearchView searchView;
    private String queryTerm = "";


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
                } else {
                    addMarkersForSearchResults(); //TODO: can also pass restaurants here
                    setupListview();
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public static Observer<List<Restaurant>> getOberserverOnRestaurants() {
        return oberserverOnRestaurants;
    }

    private void setupListview() {
        searchResultsAdapter = new SearchResultsAdapter(SearchResultsActivity.this, searchResults);
        searchResultsListView = (ListView) findViewById(R.id.searchResults_listview);
        searchResultsListView.setAdapter(searchResultsAdapter);

        //Handle item click from list view
        searchResultsListView.setClickable(true);
        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Restaurant restaurant = (Restaurant) searchResultsListView.getItemAtPosition(position);
                goToRestaurantDetails(restaurant);
            }
        });
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);

        //configure search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) searchMenuItem.getActionView(); //MenuItemCompat.getActionView(searchMenuItem)

        if (searchView != null) {
            //if results are displayed on a different activity, e.g.
            //ComponentName componentName = new ComponentName(getApplication(), RestaurantDetailsActivity.class);
            //searchable activity is the current activity
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //set the last query term, by which results are filtered
                   // && queryTerm.length() > 0
                    if (queryTerm != null) {
                        searchView.setQuery(queryTerm, false);
                    }
                }
            }
        });

        //callbacks
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //collapse search view?
                //searchMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //text has changed => apply filtering on the search results
                newText = newText.trim();
                List<Restaurant> tempSearchResults = new ArrayList<Restaurant>();
                if (newText != null) {
                    //queryTerm = newText.length() == 0 ? queryTerm : newText;
                    queryTerm = newText;
                    tempSearchResults = DataSearch.filterRestaurantContainingString(DataGenerator.generateDummyData(), newText);
                    ((SearchResultsAdapter) searchResultsAdapter).refreshRestaurants(tempSearchResults);
                }

                return true;
            }
        });

        return true;
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
            Toast.makeText(this, "Query" + query, Toast.LENGTH_SHORT).show();
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
}