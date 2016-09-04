package de.tum.pom16.teamtba.reservationapp.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.models.Restaurant;
import de.tum.pom16.teamtba.reservationapp.models.RestaurantReview;

/**
 * Created by evisa on 7/17/16.
 */
public class RestaurantReviewsViewModel implements Parcelable {
    private String restaurantName;
    private float avgRating;
    private int nrOfReviews;
    private List<RestaurantReview> reviews;

    public RestaurantReviewsViewModel(Restaurant restaurant) {
        this.restaurantName = restaurant.getName();
        this.reviews = restaurant.getReviews();
        this.avgRating = restaurant.getAverageRating();
        this.nrOfReviews = restaurant.getReviewsNr();
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getNrOfReviews() {
        return nrOfReviews;
    }

    public void setNrOfReviews(int nrOfReviews) {
        this.nrOfReviews = nrOfReviews;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public List<RestaurantReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<RestaurantReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurantName);
        dest.writeTypedList(reviews);
        dest.writeFloat(avgRating);
        dest.writeInt(nrOfReviews);

    }

    // Creator
    public static final Parcelable.Creator<RestaurantReviewsViewModel> CREATOR = new Parcelable.Creator<RestaurantReviewsViewModel>() {
        public RestaurantReviewsViewModel createFromParcel(Parcel in) {
            return new RestaurantReviewsViewModel(in);
        }

        @Override
        public RestaurantReviewsViewModel[] newArray(int size) {
            return new RestaurantReviewsViewModel[size];
        }
    };

    private RestaurantReviewsViewModel(Parcel in) {
        restaurantName = in.readString();
        reviews = new ArrayList<RestaurantReview>(); //to avoid NullPointerException
        in.readTypedList(reviews,RestaurantReview.CREATOR);
        avgRating = in.readFloat();
        nrOfReviews = in.readInt();
    }
}
