package de.tum.pom16.teamtba.reservationapp.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import de.tum.pom16.teamtba.reservationapp.activities.SearchResultsActivity;

/**
 * Created by evisa on 7/14/16.
 */

//LocationListener interface for location updates
public class LocationUtility implements ConnectionCallbacks,
                                        OnConnectionFailedListener,
                                        LocationListener,
                                        ResultCallback<LocationSettingsResult> {
    public final static int REQUEST_LOCATION = 1000;
    public final static int REQUEST_LOCATION_SETTING = 2000;

    private GoogleApiClient mLocationClient;
    private Activity activityContext;
    private Location latestLocation;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private boolean isLocationPermitted; //for the app
    private boolean isGPSEnabled; //for the device
    private Status gpsStatus; //TODO: need it or not?
    private boolean isUserInputReceivedBeforeForLocationPermission;
    private boolean isUserInputReceivedBeforeForGPSEnabling;

    private String[] locationServicesAPIPermissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION };

    public LocationUtility(Activity context) {
        this.activityContext = context;
        createLocationClient();
        createLocationRequest();
        createLocationSettingsRequest();
    }

    public synchronized void createLocationClient() {
        if (mLocationClient == null) {
            mLocationClient = new GoogleApiClient.Builder(activityContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); //10'000 ms (10 s)
        locationRequest.setFastestInterval(5000); //5000 ms (5s); 60'000 (1 min)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void createLocationSettingsRequest() {
        locationSettingsRequest= new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();
    }

    public void setmLocationClient(GoogleApiClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }

    public Location getLatestLocation() {
        return latestLocation;
    }

    public void connect() {
        if (mLocationClient != null) {
            mLocationClient.connect();
        }
    }

    public boolean isConnected() {
        return mLocationClient.isConnected();
    }

    public void disconnect() {
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkIfLocationPermitted(); //isLocationPermitted is set on this method, depending on the permissions granted
        checkLocationSettings();
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mLocationClient,
                        locationSettingsRequest
                );
        result.setResultCallback(this);
    }

    public void showDialogForPermittingLocation() {
        ActivityCompat.requestPermissions(activityContext, locationServicesAPIPermissions, REQUEST_LOCATION);
    }

    public void showDialogForEnablingGPS() {
        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            if (gpsStatus != null)
                gpsStatus.startResolutionForResult(activityContext, LocationUtility.REQUEST_LOCATION_SETTING);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        }
    }

    public void onReceivingLocationPermission(boolean wasLocationPermitted) {
        isUserInputReceivedBeforeForLocationPermission = true;
        if (!wasLocationPermitted) {
            Toast.makeText(activityContext, "Location was not permitted", Toast.LENGTH_SHORT).show();
        } else if (!isGPSEnabled) {
            //showDialogForEnablingGPS();
        } else {
            //retrieve last location
            //retrieveLastLocation();
        }
    }

    public void onReceivingGpsPermission(boolean wasGpsEnabled) {
        isUserInputReceivedBeforeForGPSEnabling = true;
        if (!wasGpsEnabled) {
            Toast.makeText(activityContext, "GPS was not enabled", Toast.LENGTH_SHORT).show();
        } else {
            //retrieve location
            //retrieveLastLocation();
        }
    }

//    public void retrieveLastLocation() {
//        if (isLocationPermitted && isGPSEnabled && ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            latestLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
//            if (latestLocation != null) {
//                Toast.makeText(activityContext, "Location EXISTS", Toast.LENGTH_SHORT).show();
//                handleNewLocation(latestLocation);
//            } else {
//                Toast.makeText(activityContext, "Null Location", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public void handleNewLocation(Location location) {
        Toast.makeText(activityContext, "YAY", Toast.LENGTH_SHORT).show();
    }

    public void setLatestLocation() {
        if (ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            latestLocation =  LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
            if (latestLocation != null) {
                ((SearchResultsActivity) activityContext).setupNearMeRestaurants(latestLocation);
            }
        }
    }

    public void removeLocationUpdates() {
        if (mLocationClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, this);
            mLocationClient.disconnect();
        }
    }

    public boolean isLocationPermitted() {
        return isLocationPermitted;
    }

    public void setLocationPermitted(boolean locationPermitted) {
        isLocationPermitted = locationPermitted;
    }

    public void checkIfLocationPermitted() {
        isLocationPermitted = ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isGPSEnabled() {
        return isGPSEnabled;
    }

    public void setGPSEnabled(boolean GPSEnabled) {
        isGPSEnabled = GPSEnabled;
    }

    @Override
    public void onConnectionSuspended(int i) {
        mLocationClient.connect(); //reconnect
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //connectionResult.getErrorCode();
        //do something
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            handleNewLocation(location);
            removeLocationUpdates(); //just receiving the 1st non-null location is enough
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        gpsStatus = status;
        isGPSEnabled = status.getStatusCode() == LocationSettingsStatusCodes.SUCCESS;
        //Other status codes: RESOLUTION_REQUIRED, SETTINGS_CHANGE_UNAVAILABLE

       if(ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && !isLocationPermitted) {
           showDialogForPermittingLocation();
       } else if (!isGPSEnabled) {
           showDialogForEnablingGPS();
       } else {
           //start location updates
           LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, locationRequest, this);
       }

    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mLocationClient,
                    locationRequest,
                    this
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    // mRequestingLocationUpdates = true;
                    //setButtonsEnabledState();
                }
            });
        }

    }
}
