package de.tum.pom16.teamtba.reservationapp.utilities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.activities.SearchResultsActivity;
import de.tum.pom16.teamtba.reservationapp.customviews.SearchResultsAdapter;
import de.tum.pom16.teamtba.reservationapp.dataaccess.DataSearch;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 10/2/16.
 */
public class SearchUtility {
    Activity activityContext;
    SearchManager searchManager;
    MenuItem searchMenuItem;
    ComponentName componentName;
    SearchView searchView;
    String queryTerm = "";

    public SearchUtility(Activity activityContext, SearchManager searchManager, MenuItem searchMenuItem, ComponentName componentName) {
        this.activityContext = activityContext;
        this.searchManager = searchManager;
        this.searchMenuItem = searchMenuItem;
        this.componentName = componentName;
        searchView = (SearchView) searchMenuItem.getActionView();
        if (searchView != null) {
            //if results are displayed on a different activity, e.g.
            //ComponentName componentName = new ComponentName(getApplication(), RestaurantDetailsActivity.class);
            //searchable activity is the current activity
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        }

        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //set the last query term, by which results are filtered
                    //&& queryTerm.length() > 0
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

                    //delegate
                    ((SearchResultsActivity)activityContext).onNewQueryTerm(newText);
                }

                return true;
            }
        });
    }


}
