package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.UpdateManager;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Constants;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 7/7/16.
 */
public class AppActivity extends AppCompatActivity {
    // Base activity class for the application
    // HockeyApp integration
    private void checkForCrashes() {
        CrashManager.register(this, Constants.HOCKEY_APP_ID);
    }

    private void checkForUpdates() {
        // Remove this for store / production builds!
        UpdateManager.register(this, Constants.HOCKEY_APP_ID);
    }

    public void showFeedbackActivity() {
        FeedbackManager.register(this, Constants.HOCKEY_APP_ID, null);
        FeedbackManager.showFeedbackActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForUpdates(); //HockeyApp

        initializeModel();
        initializeView();
    }

    protected void initializeModel() {}
    protected void initializeView() {}

    protected void setupActionBar() {
        //getSupportActionBar returns an android.support.v7.app.ActionBar;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UpdateManager.unregister();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UpdateManager.unregister();
    }

    //common transitions between screens
    public void goToRestaurantDetails(Restaurant restaurant) {
        Intent intent = new Intent(this, RestaurantOverviewActivity.class);
        intent.putExtra(Constants.RESTAURANT_DETAILS, restaurant);
        startActivity(intent);
    }

    protected void returnToSearchResults() {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }
}
