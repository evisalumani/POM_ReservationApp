package de.tum.pom16.teamtba.reservationapp.activities;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.SearchResultsAdapter;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataGenerator;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataSearch;
import de.tum.pom16.teamtba.reservationapp.dataaccess.SortByDistance;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.utilities.*;

public class SearchResultsActivity extends MapCallbackActivity {
    //view
    ListView searchResultsListView;
    ListAdapter searchResultsAdapter;

    private static final int ZOOM_LEVEL = 15;

    //model
    List<Restaurant> searchResults;
    LocationUtility locationUtility;

    private GoogleApiClient googleApiClient;
    private Location userLatestLocation;

    SearchView searchView;
    private String queryTerm = "";


    @Override
    protected void initializeModel() {
        super.initializeModel();

        //locationUtility = new LocationUtility(this);

        Intent intent = getIntent();
        List<Restaurant> filteredRestaurants = intent.getParcelableArrayListExtra(IntentType.INTENT_FILTER_TO_SEARCH_RESULTS.name());

        //TODO: logic for displaying a message "no results matching filters"
        if (filteredRestaurants != null) {
            //display filtered restaurants
            searchResults = filteredRestaurants;
        } else {
            //display all restaurants
            searchResults = DataGenerator.generateDummyData();
        }
    }

    @Override
    protected void initializeView() {
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addMarkersForSearchResults();

        searchResultsAdapter = new SearchResultsAdapter(this, searchResults); //TODO: null check?
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

        super.initializeView();
    }

    @Override
    protected void onStart() {

        super.onStart();
        locationUtility.connect();
        //googleApiClient.connect();
    }

    @Override
    protected void onStop() {


        super.onStop();
        if (locationUtility.isConnected()) {
            locationUtility.disconnect();
        }
//        if (googleApiClient.isConnected()) {
//            googleApiClient.disconnect();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationUtility.removeLocationUpdates();
    }

    public void filterSearchResults(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LocationUtility.REQUEST_LOCATION) {
            //length == 1 before
            //set isLocationPermitted boolean value, depending on whether the permission was granted or denied from the user via the dialog
            locationUtility.setLocationPermitted(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case LocationUtility.REQUEST_LOCATION_SETTING:
                //Activity_RESULT_OK, Activity_RESULT_CANCELED
                locationUtility.setGPSEnabled(resultCode == Activity.RESULT_OK);
                locationUtility.retrieveLastLocation();
                break;
        }
    }

    public void setupNearMeRestaurants(Location latestLocation) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && latestLocation != null) {
            //sort by distance to user
            //searchResults = DataSort.sortRestaurantsByDistanceFromUser(searchResults, latestLocation);

            //TODO: refactoring
            SortByDistance sortByDistance = new SortByDistance(true, latestLocation);
            sortByDistance.setRestaurants(searchResults);
            searchResults = sortByDistance.sort();

            //notify changes to the listview
            ((SearchResultsAdapter) searchResultsAdapter).refreshRestaurants(searchResults);

            //show user location
            setUserLocationEnabled(true);
            //move camera to user location
            moveCameraToPosition(new LatLng(latestLocation.getLatitude(), latestLocation.getLongitude()), ZOOM_LEVEL, true);
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
//
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
        //buildGoogleApiClient();
    }


    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            Toast.makeText(this, "Query" + query, Toast.LENGTH_SHORT).show();
            //doSearch(query)
        }
    }

//    protected synchronized void buildGoogleApiClient() {
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        //GoogleAPIClient is connected
//        userLatestLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if (userLatestLocation != null) {
//            Toast.makeText(this, "Location: " + userLatestLocation.getLatitude() + ", " + userLatestLocation.getLongitude(), Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        //connection suspended
//        googleApiClient.connect(); //reconnect
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        //connectionResult.getErrorCode();
//        //do something
//    }
}