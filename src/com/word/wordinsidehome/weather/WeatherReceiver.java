package com.word.wordinsidehome.weather;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.List;

import android.app.ActivityManager;
import android.app.SystemWriteManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.ScanResult;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManagerPolicy;
import android.os.Handler;
import android.os.Message;

public class WeatherReceiver extends BroadcastReceiver {

	private static final String TAG = "WeatherReceiver";
	Context mContext = null;
	private SystemWriteManager sw;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "==== BootReceiver , action : " + intent.getAction());
		mContext = context;
		sw = (SystemWriteManager) mContext.getSystemService("system_write");
        String action = intent.getAction();
		if ("android.intent.action.BOOT_COMPLETED".equals(action)) {			
    }
    

        //=================for weather 
		if ("android.windInside.launcher.REQUEST_WEATHER".equals(action)) {
			Log.d("zzkw","===== receive REQUEST_WEATHER ");
			new WeatherBroadcastThread(mContext).start();
		}

		if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            String value = sw.getPropertyString("sys.weather.send", "false");
            if("false".equals(value)){
			      upDateWeather();
            }
		}

	}


	void upDateWeather() {
		State wifiState = null;
		State mobileState = null;
        State ethState = null;
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        ethState = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).getState();
		if ((wifiState != null && State.CONNECTED == wifiState) || (ethState != null && State.CONNECTED == ethState)) {
			Log.d(TAG, "wifi connect , send weather info right now !!!");
			new WeatherBroadcastThread(mContext).start();
		}

	}


}
