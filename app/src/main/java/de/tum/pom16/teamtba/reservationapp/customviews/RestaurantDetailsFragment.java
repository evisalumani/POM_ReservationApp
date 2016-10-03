package de.tum.pom16.teamtba.reservationapp.customviews;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;

import de.tum.pom16.teamtba.reservationapp.R;
import de.tum.pom16.teamtba.reservationapp.models.Restaurant;

/**
 * Created by evisa on 9/22/16.
 */
public class RestaurantDetailsFragment extends PlaceholderFragment implements OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap googleMap;
    private TextView cuisineTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;

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

            cuisineTextView.setText(restaurant.getType().name().toLowerCase());
            priceTextView.setText("Price " + restaurant.getPriceCategoryStr());
            descriptionTextView.setText(restaurant.getDescription());
        }
    }
}
