package de.tum.pom16.teamtba.reservationapp.customviews;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.activities.AppActivity;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;
import de.tum.pom16.teamtba.reservationapp.utilities.MapUtility;

/**
 * Created by evisa on 10/9/16.
 */
public class SearchResultsFragment extends Fragment {
    ListView searchResultsListView;
    ListAdapter searchResultsAdapter;
    List<Restaurant> searchResults;
    MapUtility mapUtility;
    SupportMapFragment mapFragment;

    public SearchResultsFragment() {
        mapUtility = new MapUtility();
    }

    public SearchResultsFragment(List<Restaurant> searchResults) {
        mapUtility = new MapUtility(); //Redundant?
        this.searchResults = searchResults;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_map_listview_search_results, container, false);
        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        searchResultsListView = (ListView) v.findViewById(R.id.searchResults_listview);

        if (searchResults != null && searchResults.size() > 0) {
            searchResultsAdapter = new SearchResultsAdapter(getActivity(), Helpers.deepCopyRestaurants(searchResults));
            searchResultsListView.setAdapter(searchResultsAdapter);

            //Handle item click from list view
            searchResultsListView.setClickable(true);
            searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Restaurant restaurant = (Restaurant) searchResultsListView.getItemAtPosition(position);
                    ((AppActivity)getActivity()).goToRestaurantDetails(restaurant);
                }
            });

        }
        return v;
    }
}
