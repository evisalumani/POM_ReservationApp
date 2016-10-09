package de.tum.pom16.teamtba.reservationapp.utilities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.tum.pom16.teamtba.reservationapp.dataaccess.GlobalSearchFilters;

/**
 * Created by evisa on 10/9/16.
 */
public class MapUtility implements OnMapReadyCallback {
    //TODO: can implement MapUtilityInterface -> later work
    private static final int ZOOM_LEVEL = 15;
    private GoogleMap googleMap;
    private List<MarkerOptions> markers;

    public MapUtility() {
        markers = new ArrayList<MarkerOptions>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Enable zoom function
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        //TODO: getmapasync, draw markers after getting search results
        //draw markers
        drawMarkers();

        //show user location
        //setUserLocationEnabled(true);
        //move camera to the location by which we're filtering
        if (GlobalSearchFilters.getSharedInstance().getLocationToFilter() != null)
            moveCameraToPosition(new LatLng(GlobalSearchFilters.getSharedInstance().getLocationToFilter().getLatitude(), GlobalSearchFilters.getSharedInstance().getLocationToFilter().getLongitude()), ZOOM_LEVEL, true);

    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap map) {
        this.googleMap = map;
    }

    public void drawMarkers() {
        if (googleMap != null && markers != null && markers.size() > 0) {
            for (MarkerOptions marker : markers) {
                googleMap.addMarker(marker);
            }
        }
    }

    public void drawMarker(MarkerOptions marker) {
        if (googleMap != null) {
            googleMap.addMarker(marker);
        }
    }

    public void addMarker(double lat, double lng, String name) {
        if (markers != null) {
            LatLng latLng = new LatLng(lat, lng);
            MarkerOptions marker = new MarkerOptions().position(latLng).title(name);
            markers.add(marker);
        }
    }

    public void moveCameraToPosition(LatLng position, float zoomLevel, boolean animate) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, zoomLevel);
        if (googleMap != null) {
            if (animate)
                googleMap.animateCamera(update);
            else
                googleMap.moveCamera(update);
        }
    }

//    public void setUserLocationEnabled(boolean enabled) {
//        if (ActivityCompat.checkSelfPermission(, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(enabled);
//        }
//    }

}