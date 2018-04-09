package com.kalata.peter.bakingapp.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.kalata.peter.bakingapp.App;

public final class NetworkUtils {

    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm == null ? null : cm.getActiveNetworkInfo();
    }

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private static boolean isConnected(NetworkInfo info) {
        return info != null && info.isConnected();
    }

    public static boolean isConnectedToWifi() {
        NetworkInfo info = getActiveNetworkInfo();
        return isConnected(info) && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isConnectedToMobile(Context context) {
        NetworkInfo info = getActiveNetworkInfo();
        return isConnected(info) && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

}
