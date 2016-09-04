package de.tum.pom16.teamtba.reservationapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.customviews.SearchResultsAdapter;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataGenerator;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataSort;
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

    @Override
    protected void initializeModel() {
        super.initializeModel();

        locationUtility = new LocationUtility(this);

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
        super.initializeView();
        setContentView(R.layout.activity_search_results);

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
    }

    @Override
    protected void onStart() {
        locationUtility.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (locationUtility.isConnected()) {
            locationUtility.disconnect();
        }

        super.onStop();
    }

    public void filterSearchResults(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LocationUtility.REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // user has granted permission
                locationUtility.setLatestLocation();
            } else {
                // Permission was denied or request was cancelled
                Toast.makeText(this.getApplicationContext(), "Location permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setupNearMeRestaurants(Location latestLocation) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && latestLocation != null) {
            //sort by distance to user
            searchResults = DataSort.sortRestaurantsByDistanceFromUser(searchResults, latestLocation);

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
}