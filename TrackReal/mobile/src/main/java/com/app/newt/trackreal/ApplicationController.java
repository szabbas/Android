/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal;

import java.util.HashMap;
import java.util.Map;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

/**
 * Created by Rajeev on 21/12/15.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class ApplicationController extends Application {

    // Constants identifying device and sim info.
    public static final String MOB_NO = "MOB_NO";
    public static final String SIM_CARRIER = "SIM_CARRIER";
    public static final String SUBSCRIBER_ID = "IMSI_NO";
    public static final String DEVICE_MAKE = "DEVICE_MAKE";
    public static final String DEVICE_MODEL = "DEVICE_MODEL";
    public static final String DEVICE_IMEI = "DEVICE_IMEI";
    public static final String DEVICE_OS = "DEVICE_OS";
    public static final String DEVICE_COUNTRY_CODE = "DEVICE_COUNTRY_CODE";

    /**
     * A singleton instance of the application class for easy access in other
     * places
     */
    private static ApplicationController sInstance;

    TelephonyManager mTelManager = null;

    ConnectivityManager mConManager = null;

    /**
     * Holds the device info
     */
    Map<String, String> mDeviceInfo = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the singleton
        sInstance = this;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }

    /**
     * Returns telephony manager instance
     *
     * @return telephony class instance.
     */
    public TelephonyManager getTelephonyManager() {

        if (mTelManager == null) {
            mTelManager = (TelephonyManager) getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }

        return mTelManager;
    }

    /**
     * Returns connectivity manager instance
     *
     * @return Connectivity manager class instance.
     */
    public ConnectivityManager getConnectivityManager() {

        if (mConManager == null) {
            mConManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return mConManager;
    }

    /**
     * Returns a map containing the devices information. Key are the constants
     * defined.
     *
     * @return Map holding device info
     */
    public Map<String, String> getDeviceInfo() {

        if (mDeviceInfo == null) {
            mDeviceInfo = new HashMap<>();
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                mDeviceInfo.put(DEVICE_IMEI,
                        (getTelephonyManager().getDeviceId() == null ? ""
                                : getTelephonyManager().getDeviceId()));
                mDeviceInfo.put(DEVICE_MAKE, (Build.MANUFACTURER == null ? ""
                        : Build.MANUFACTURER));
                mDeviceInfo.put(DEVICE_MODEL, (Build.MODEL == null ? ""
                        : Build.MODEL));
                mDeviceInfo.put(SIM_CARRIER, (getTelephonyManager()
                        .getSimOperatorName() == null ? "" : getTelephonyManager()
                        .getSimOperatorName()));
                mDeviceInfo.put(MOB_NO,
                        (getTelephonyManager().getLine1Number() == null ? ""
                                : getTelephonyManager().getLine1Number()));
                mDeviceInfo.put(SUBSCRIBER_ID, (getTelephonyManager()
                        .getSubscriberId() == null ? "" : getTelephonyManager()
                        .getSubscriberId()));
                mDeviceInfo.put(DEVICE_OS, Build.VERSION.RELEASE == null ? ""
                        : "Android " + Build.VERSION.RELEASE);
                mDeviceInfo.put(DEVICE_COUNTRY_CODE, (getTelephonyManager()
                        .getSimCountryIso() == null ? "" : getTelephonyManager()
                        .getSimCountryIso()));
            }
        }
        return mDeviceInfo;
    }

    /**
     * Checks whether the network is available or not.
     *
     * @return true is network is available otherwise false
     */
    public boolean isNetworkAvailable() {

        NetworkInfo activeNetworkInfo = getConnectivityManager()
                .getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}