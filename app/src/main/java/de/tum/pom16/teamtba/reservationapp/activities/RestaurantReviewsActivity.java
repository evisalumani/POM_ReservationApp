//package de.tum.pom16.teamtba.reservationapp.activities;
//
//import android.content.Intent;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//import de.tum.pom16.teamtba.reservationapp.R;
//import de.tum.pom16.teamtba.reservationapp.customviews.RestaurantReviewAdapter;
//import de.tum.pom16.teamtba.reservationapp.viewmodels.RestaurantReviewsViewModel;
//
//public class RestaurantReviewsActivity extends AppActivity {
//
//    //model
//    private RestaurantReviewsViewModel reviewsViewModel;
//
//    //view
//    ListAdapter restaurantReviewsAdapter;
//    ListView reviewsListView;
//
//    @Override
//    protected void initializeModel() {
//        super.initializeModel();
//        Intent intent = this.getIntent();
//        reviewsViewModel = (RestaurantReviewsViewModel)intent.getParcelableExtra(IntentType.INTENT_DETAILS_TO_REVIEWS.name());
//    }
//
//    @Override
//    protected void initializeView() {
//        super.initializeView();
//        setContentView(R.layout.activity_restaurant_reviews);
//
//        if (reviewsViewModel != null) {
//            TextView restaurantNameTextView = (TextView) findViewById(R.id.restaurant_reviews_restaurantName);
//            restaurantNameTextView.setText(reviewsViewModel.getRestaurantName().toUpperCase() + " Restaurant");
//
//            TextView nrOfReviewsTextView = (TextView) findViewById(R.id.restaurant_reviews_nrOfReviews);
//            nrOfReviewsTextView.setText(String.valueOf(reviewsViewModel.getNrOfReviews()) + " Reviews");
//
//            RatingBar ratingBar = (RatingBar) findViewById(R.id.restaurant_reviews_ratingbar);
//            ratingBar.setRating(reviewsViewModel.getAvgRating());
//
//            TextView avgRatingTextView = (TextView) findViewById(R.id.restaurant_reviews_avgRating);
//            avgRatingTextView.setText(String.valueOf(reviewsViewModel.getAvgRating()));
//
//            if (reviewsViewModel.getReviews() != null) {
//                restaurantReviewsAdapter = new RestaurantReviewAdapter(this, reviewsViewModel.getReviews());
//                reviewsListView = (ListView) findViewById(R.id.restaurant_reviews_listview);
//                reviewsListView.setAdapter(restaurantReviewsAdapter);
//            }
//        }
//    }
//}
