package de.tum.pom16.teamtba.reservationapp.customviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.OpeningTimes;
import de.tum.pom16.teamtba.reservationapp.utilities.Helpers;

/**
 * Created by evisa on 9/22/16.
 */
public class RestaurantDetailsFragment extends PlaceholderFragment implements OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap googleMap;
    private TextView cuisineTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView addressLine1TextView;
    private TextView addressLine2TextView;
    private TextView distance1TextView;
    private TextView distance2TextView;
    private LinearLayout openingTimesLayout;

    public RestaurantDetailsFragment() {
        super();
    }
    //Note: property on base class about an int resourceID and constructor taking this as an argument are removed
    //in order to avoid the crash on screen rotation, when resourceID is 0 onCreate() method
    //TODO: I think the issue can be fixed, now that setRetainInstance(boolean retain) is set to true
    //-> think about including a property for resource ID

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_details, container, false);

        //solution here, following instructions: http://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
        //response from Richard
        cuisineTextView = (TextView)v.findViewById(R.id.restaurant_details_cuisine_textview);
        priceTextView = (TextView)v.findViewById(R.id.restaurant_details_price_textview);
        descriptionTextView = (TextView)v.findViewById(R.id.restaurant_details_description_textview);
        addressLine1TextView = (TextView)v.findViewById(R.id.restaurant_details_address_firstLine);
        addressLine2TextView = (TextView)v.findViewById(R.id.restaurant_details_address_secondLine);
        distance1TextView = (TextView)v.findViewById(R.id.restaurant_details_distance_firstLine);
        distance2TextView = (TextView)v.findViewById(R.id.restaurant_details_distance_secondLine);
        openingTimesLayout = (LinearLayout) v.findViewById(R.id.restaurant_details_opening_times_layout);

        mMapView = (MapView) v.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void setupOpeningTimesLayout() {
        //Stream.of(restaurant.getOpeningTimes().entrySet()).sorted((entry1, entry2) -> Integer.compare(entry1.getKey(), entry2.getKey()));
        for (int i=1; i<8; i++) {
            if (restaurant.getOpeningTimes().get(i) != null) {
                TextView txtView = new TextView(getActivity());
                txtView.setText(Helpers.getDayOfWeekString()[i] + ", " + restaurant.getOpeningTimes().get(i).toString());
                txtView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.RIGHT_OF));
                openingTimesLayout.addView(txtView);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setZoomGesturesEnabled(true);
        updateUIForRestaurant();
    }

    private void updateUIForRestaurant() {
        if (restaurant != null) {
            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude())).title(restaurant.getName());

            // adding marker
            googleMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(restaurant.getLatitude(), restaurant.getLongitude())).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            addressLine1TextView.setText(restaurant.getAddress());
            double distanceRounded = Math.round((restaurant.getDistanceFromUserLocation()/1000.0) * 100.0) / 100.0;
            distance1TextView.setText(String.valueOf(distanceRounded));
            distance2TextView.setText("km away");
            cuisineTextView.setText("Cuisine: " + restaurant.getType().name().toLowerCase());
            priceTextView.setText("Price: " + restaurant.getPriceCategoryStr());
            descriptionTextView.setText(restaurant.getDescription());

            setupOpeningTimesLayout();
        }
    }
}
