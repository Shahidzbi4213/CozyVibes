package com.gulehri.edu.pk.mildvideos.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Shahid Iqbal on 10,November,2021
 */
public class CheckConnection {

    private final Context mContext;

    public CheckConnection(Context mContext) {
        this.mContext = mContext;
    }

    public boolean haveNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected());
    }
}
