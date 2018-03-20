package com.example.keval.e_moss.Utils;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class GoogleServiceHelperClass {

    private Activity mActivity;

    public GoogleServiceHelperClass(Activity activity) {
        mActivity = activity;
    }

    public int getErrorCode() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);
    }

    public boolean isAvailable() {
        return getErrorCode() == ConnectionResult.SUCCESS;
    }

    public boolean checkPlayServices() {
        if (isAvailable())
            return true;

        final int errorCode = getErrorCode();

        GooglePlayServicesUtil.getErrorDialog(errorCode, mActivity,
                200).show();

        return false;
    }
}
