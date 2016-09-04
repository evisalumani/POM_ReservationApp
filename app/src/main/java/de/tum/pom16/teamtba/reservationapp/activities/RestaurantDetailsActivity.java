package de.tum.pom16.teamtba.reservationapp.activities;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.viewmodels.RestaurantReviewsViewModel;

/**
 * Created by hamed on 04/06/16.
 */
public class RestaurantDetailsActivity extends MapCallbackActivity {
    Restaurant restaurant;

    @Override
    protected void initializeModel() {
        super.initializeModel();
        Intent mIntent = getIntent();
        restaurant = (Restaurant) mIntent.getParcelableExtra(IntentType.INTENT_TO_RESTAURANT_DETAILS.name());
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        setContentView(R.layout.activity_restaurant_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.restaurantDetails_map);
        mapFragment.getMapAsync(this);

        if (restaurant != null) {
            setupRestaurantUIView(restaurant);
        }
    }

    private void setupRestaurantUIView(Restaurant restaurant) {
        ((TextView) findViewById(R.id.restaurantDetails_name_picture_textview)).setText(restaurant.getName());
        ((TextView) findViewById(R.id.restaurantDetails_name_textview)).setText(restaurant.getName());
        ((TextView) findViewById(R.id.restaurantDetails_type_textview)).setText(restaurant.getType().name());
        ((TextView) findViewById(R.id.restaurantDetails_averageRatingPrefix_textview)).setText(String.valueOf(restaurant.getAverageRating()));
        ((TextView) findViewById(R.id.restaurantDetails_price_textview)).setText(String.valueOf(restaurant.getAveragePrice()));
        ((TextView) findViewById(R.id.restaurantDetails_description_textview)).setText(restaurant.getDescription());

        addMarker(restaurant.getLatitude(), restaurant.getLongitude(), restaurant.getName());
    }

    public void goToReservation(View view) {
        Intent intent = new Intent(this, ReservationActivity.class);
        intent.putExtra(IntentType.INTENT_DETAILS_TO_RESERVATION.name(), restaurant);
        startActivity(intent);
    }

    public void showRestaurantReviews(View view) {
        if (restaurant != null) {
            //create view model
            RestaurantReviewsViewModel viewModel = new RestaurantReviewsViewModel(restaurant);

            Intent intent = new Intent(this, RestaurantReviewsActivity.class);
            intent.putExtra(IntentType.INTENT_DETAILS_TO_REVIEWS.name(), viewModel);
            startActivity(intent);
        }
    }

}