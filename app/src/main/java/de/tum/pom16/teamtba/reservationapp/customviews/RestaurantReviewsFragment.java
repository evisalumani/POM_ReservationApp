package de.tum.pom16.teamtba.reservationapp.customviews;

import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import de.tum.pom16.teamtba.reservationapp.R;

/**
 * Created by evisa on 9/22/16.
 */
public class RestaurantReviewsFragment extends PlaceholderFragment {
    private View fragmentView;
    private TextView averageRatingTextView;
    private RatingBar averageRatingRatingBar;
    private TextView totalNrOfReviewsTextView;

    private ProgressBar[] progressBars;

    ListAdapter restaurantReviewsAdapter;
    ListView reviewsListView;

    public RestaurantReviewsFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_restuarant_reviews, container, false);

        if (restaurant != null && restaurant.getReviews() != null && restaurant.getReviews().size() > 0) {
            initializeView();
        }

        return fragmentView;
    }

    private void initializeView() {
        averageRatingTextView = (TextView)fragmentView.findViewById(R.id.reviews_average_textview);
        averageRatingTextView.setText(String.valueOf(restaurant.getAverageRating()));
        averageRatingRatingBar = (RatingBar)fragmentView.findViewById(R.id.reviews_average_ratingbar);
        averageRatingRatingBar.setRating(restaurant.getAverageRating());
        totalNrOfReviewsTextView = (TextView)fragmentView.findViewById(R.id.reviews_totalReviewsNr_textview);
        totalNrOfReviewsTextView.setText(restaurant.getReviewsNr() + " Review" + (restaurant.getReviewsNr() <= 1 ? "" : "s"));

        progressBars = new ProgressBar[5]; //5 progress bars for five stars
        progressBars[0] = (ProgressBar)fragmentView.findViewById(R.id.reviews_one_progressbar);
        progressBars[1] = (ProgressBar)fragmentView.findViewById(R.id.reviews_two_progressbar);
        progressBars[2] = (ProgressBar)fragmentView.findViewById(R.id.reviews_three_progressbar);
        progressBars[3] = (ProgressBar)fragmentView.findViewById(R.id.reviews_four_progressbar);
        progressBars[4] = (ProgressBar)fragmentView.findViewById(R.id.reviews_five_progressbar);

        int[] reviewsDistribution = restaurant.getReviewsDistribution();
        if (reviewsDistribution != null) {
            for (int i=0; i<5; i++) {
                progressBars[i].setProgress((reviewsDistribution[i]*100)/restaurant.getReviewsNr());
            }
        }

        restaurantReviewsAdapter = new RestaurantReviewAdapter(getActivity(), restaurant.getReviews());
        reviewsListView = (ListView)fragmentView.findViewById(R.id.reviews_listview);
        reviewsListView.setAdapter(restaurantReviewsAdapter);
    }
}
