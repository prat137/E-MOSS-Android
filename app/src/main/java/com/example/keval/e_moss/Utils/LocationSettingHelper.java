package com.example.keval.e_moss.Utils;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by smartsense on 27/09/16.
 */

public class LocationSettingHelper implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult> {

    private Activity mActivity;
    private GoogleApiClient mGoogleClient;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    public LocationSettingHelper(Activity context) {
        mActivity = context;
    }

    public void checkSettings() {
        GoogleServiceHelperClass mPlayServicesHelper = new GoogleServiceHelperClass(
                mActivity);

        if (!mPlayServicesHelper.checkPlayServices())
            return;

        mGoogleClient = new GoogleApiClient.Builder(mActivity)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mActivity == null)
            return;
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(15000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleClient, builder.build());

        result.setResultCallback(this);
    }

    @Override
    public void onResult(LocationSettingsResult result) {
        final Status status = result.getStatus();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


}
