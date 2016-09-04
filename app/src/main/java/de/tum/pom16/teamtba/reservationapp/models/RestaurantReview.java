package de.tum.pom16.teamtba.reservationapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by evisa on 7/12/16.
 */
public class RestaurantReview implements Parcelable {
    private String user;
    private float rating;
    private Date date;
    private String description;

    public RestaurantReview(float rating, String user, Date date, String description) {
        this.rating = rating;
        this.user = user;
        this.date = date;
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Creator
    public static final Parcelable.Creator<RestaurantReview> CREATOR = new Parcelable.Creator<RestaurantReview>() {
        public RestaurantReview createFromParcel(Parcel in) {
            return new RestaurantReview(in);
        }

        @Override
        public RestaurantReview[] newArray(int size) {
            return new RestaurantReview[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeFloat(rating);
        dest.writeString(description);
    }

    private RestaurantReview(Parcel in) {
        user = in.readString();
        rating = in.readFloat();
        description = in.readString();
    }
}
