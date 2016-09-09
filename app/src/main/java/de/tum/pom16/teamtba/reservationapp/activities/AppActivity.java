package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.UpdateManager;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.utilities.HockeyAppIntegration;

/**
 * Created by evisa on 7/7/16.
 */
public class AppActivity extends AppCompatActivity {
    //display action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_hockeyapp_feedback:
                showFeedbackActivity();
                break;
            default:
                break;
        }

        return true;
    }

    //HockeyApp integration
    private void checkForCrashes() {
        CrashManager.register(this, HockeyAppIntegration.HOCKEY_APP_ID);
    }

    private void checkForUpdates() {
        // Remove this for store / production builds!
        UpdateManager.register(this, HockeyAppIntegration.HOCKEY_APP_ID);
    }

    public void showFeedbackActivity() {
        FeedbackManager.register(this, HockeyAppIntegration.HOCKEY_APP_ID, null);
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
    protected void goToRestaurantDetails(Restaurant restaurant) {
        Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        intent.putExtra(IntentType.INTENT_TO_RESTAURANT_DETAILS.name(), restaurant);
        startActivity(intent);
    }
}
