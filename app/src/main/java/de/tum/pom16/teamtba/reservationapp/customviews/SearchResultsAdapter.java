package de.tum.pom16.teamtba.reservationapp.customviews;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 5/24/16.
 */
public class SearchResultsAdapter extends ArrayAdapter<Restaurant> {
    List<Restaurant> restaurants;

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
        ImageView imageView = (ImageView)searchResultRowView.findViewById(R.id.searchResult_image);
        RatingBar ratingBar = (RatingBar) searchResultRowView.findViewById(R.id.searchResult_ratingbar);
        TextView nameText = (TextView)searchResultRowView.findViewById(R.id.searchResult_restaurantName);
        TextView descriptionText = (TextView)searchResultRowView.findViewById(R.id.searchResult_restaurantDescription);

        //set UI elements
        nameText.setText(searchResult.getName());
        descriptionText.setText(searchResult.getShortDescription());

        // TODO: load corresponding image
        imageView.setImageResource(R.drawable.ic_local_dining);
        ratingBar.setRating(searchResult.getAverageRating());

        return searchResultRowView;
    }

    public void refreshRestaurants(List<Restaurant> restaurants) {
        this.restaurants.clear();
        this.restaurants.addAll(restaurants);
        notifyDataSetChanged();
    }
}
