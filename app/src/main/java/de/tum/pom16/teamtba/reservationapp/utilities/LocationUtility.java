package de.tum.pom16.teamtba.reservationapp.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationServices;

import de.tum.pom16.teamtba.reservationapp.activities.SearchResultsActivity;

/**
 * Created by evisa on 7/14/16.
 */

//LocationListener interfce for location updates
public class LocationUtility implements ConnectionCallbacks, OnConnectionFailedListener {
    private GoogleApiClient mLocationClient;
    private Activity activityContext;
    private Location latestLocation;
    public static int REQUEST_LOCATION = 1000;

    public LocationUtility(Activity context) {
        this.activityContext = context;
        createLocationClient();
    }

    public void createLocationClient() {
        if (mLocationClient == null) {
            //create new
            mLocationClient = new GoogleApiClient.Builder(activityContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void setmLocationClient(GoogleApiClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }

    public Location getLatestLocation() {
        return latestLocation;
    }

    public void connect() {
        mLocationClient.connect();
    }

    public boolean isConnected() {
        return mLocationClient.isConnected();
    }

    public void disconnect() {
        mLocationClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // check and require permission
            // 1st argument requires Activity
            ActivityCompat.requestPermissions(activityContext, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual
            setLatestLocation();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                setLatestLocation();
            } else {
                // Permission was denied or request was cancelled
                Toast.makeText(activityContext, "Location permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setLatestLocation() {
        if (ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            latestLocation =  LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
            if (latestLocation != null) {
                ((SearchResultsActivity) activityContext).setupNearMeRestaurants(latestLocation);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
