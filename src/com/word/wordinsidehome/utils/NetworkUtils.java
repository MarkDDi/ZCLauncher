package com.word.wordinsidehome.utils;

import android.util.Log;
import android.content.Context;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static boolean isNetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Log.d(TAG, "connect the Internet ok!");
            return true;
        } else {
            Log.d(TAG, "It's can't connect the Internet!");
            return false;
        }
    }

}
