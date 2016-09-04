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
import de.tum.pom16.teamtba.reservationapp.models.RestaurantReview;

/**
 * Created by evisa on 7/12/16.
 */
public class RestaurantReviewAdapter extends ArrayAdapter<RestaurantReview> {
    List<RestaurantReview> reviews;

    public RestaurantReviewAdapter(Context context, List<RestaurantReview> reviews) {
        super(context, R.layout.restaurant_review_row, reviews);
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View restaurantReviewRow = inflater.inflate(R.layout.restaurant_review_row, parent, false);

        //model
        RestaurantReview restaurantReview = getItem(position);

        //UI
        TextView user = (TextView) restaurantReviewRow.findViewById(R.id.review_row_user);
        RatingBar ratingBar = (RatingBar) restaurantReviewRow.findViewById(R.id.review_row_ratingbar);
        TextView description = (TextView) restaurantReviewRow.findViewById(R.id.review_row_description);

        user.setText(restaurantReview.getUser());
        ratingBar.setRating(restaurantReview.getRating());
        description.setText(restaurantReview.getDescription());
        return restaurantReviewRow;
    }
}
