package com.codelabs.sitepat_customer.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;

public class LocationReceiver extends BroadcastReceiver {

    private String TAG = "LOCATION RECEIVER";

    private LocationResult mLocationResult;
    private Context context;
    Location mLastLocation;


    @Override
    public void onReceive(Context context, Intent intent) {
        // Need to check and grab the Intent's extras like so
        this.context = context;
        if (LocationResult.hasResult(intent)) {
            this.mLocationResult = LocationResult.extractResult(intent);
            if (mLocationResult.getLocations().get(0).getAccuracy() < 100) {

                // DO WHATEVER YOU WANT WITH LOCATION
            }
        }
    }
}
