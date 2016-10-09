package de.tum.pom16.teamtba.reservationapp.customviews;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 5/24/16.
 */
public class SearchResultsAdapter extends ArrayAdapter<Restaurant> {
    private List<Restaurant> restaurants;

    public SearchResultsAdapter(Context context, List<Restaurant> restaurants) {
        //note: restaurants has to be non null
        super(context, R.layout.search_result, restaurants);
        this.restaurants = restaurants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View searchResultRowView = inflater.inflate(R.layout.search_result, parent, false);

        //model
        Restaurant searchResult = getItem(position);

        //custom row view UI
        //TODO: load corresponding image (e.g. later from the db)
        TextView nameTextView = (TextView)searchResultRowView.findViewById(R.id.searchResult_restaurantName);
        RatingBar ratingBar = (RatingBar) searchResultRowView.findViewById(R.id.searchResult_ratingbar);
        TextView nrOfReviewsTextView = (TextView)searchResultRowView.findViewById(R.id.searchResult_nrOfReviews);
        TextView priceCategory = (TextView)searchResultRowView.findViewById(R.id.searchResult_price_category);
        TextView cuisineTextView = (TextView)searchResultRowView.findViewById(R.id.searchResult_cuisine);
        TextView distanceTextView = (TextView)searchResultRowView.findViewById(R.id.searchResult_distance);

        //set UI elements
        nameTextView.setText(searchResult.getName());
        ratingBar.setRating(searchResult.getAverageRating());
        nrOfReviewsTextView.setText("(" + searchResult.getReviewsNr() + ")");
        priceCategory.setText(searchResult.getPriceCategoryStr());
        cuisineTextView.setText(searchResult.getType().name());
        distanceTextView.setText(searchResult.getRoundedDistanceFromUserLocation() + " km");

        return searchResultRowView;
    }

    public void refreshRestaurants(List<Restaurant> restaurants) {
        this.restaurants.clear();
        this.restaurants.addAll(restaurants);
        notifyDataSetChanged();
    }
}
