package de.tum.pom16.teamtba.reservationapp.utilities;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by evisa on 10/9/16.
 */
public interface MapUtilityInterface extends OnMapReadyCallback{
    void setUserLocationEnabled(boolean enabled);
    void moveCameraToPosition(LatLng position, float zoomLevel, boolean animate);
    void addMarker(double lat, double lng, String name);
    void drawMarker(MarkerOptions marker);
}
