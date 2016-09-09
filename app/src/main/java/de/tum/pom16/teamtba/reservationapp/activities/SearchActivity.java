package de.tum.pom16.teamtba.reservationapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;


import java.util.List;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataGenerator;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataSearch;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;


/**
 * Created by evisa on 6/5/16.
 */
public class SearchActivity extends AppActivity {
    //view
    AutoCompleteTextView searchTextView;

    //model
    List<Restaurant> restaurants;
    List<String> restaurantNames;

    @Override
    protected void initializeModel() {
        super.initializeModel();
        restaurants = DataGenerator.generateDummyData();
        restaurantNames = Stream.of(restaurants).map(restaurant -> restaurant.getName()).collect(Collectors.toList());
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        setContentView(R.layout.activity_search);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, restaurantNames);
        searchTextView = (AutoCompleteTextView) findViewById(R.id.search_name_textview);
        searchTextView.setThreshold(2);
        searchTextView.setAdapter(adapter);
    }

    private void goToSearchResults(List<Restaurant> restaurants) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }

    public void searchRestaurantByName(View view) {
        String restaurantName = searchTextView.getText().toString();
        if (restaurants != null && restaurantName != null && restaurantName.length() > 0) {
            Optional<Restaurant> foundRestaurant = DataSearch.filterRestaurantByName(restaurants, restaurantName);

            if (foundRestaurant.isPresent()) {
                //display specific restaurant
                goToRestaurantDetails(foundRestaurant.get());
            } else {
                //display all restaurants
                goToSearchResults(restaurants);
            }
        }
    }

    public void searchNearMe(View view) {
        if (restaurants != null) {
            goToSearchResults(restaurants);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        //searchView.setIconifiedByDefault(true);
        return true;
    }
}
