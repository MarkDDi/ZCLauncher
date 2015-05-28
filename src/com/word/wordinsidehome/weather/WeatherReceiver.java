package com.word.wordinsidehome.weather;

//import android.app.SystemWriteManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;

import com.word.wordinsidehome.utils.LogUtils;

public class WeatherReceiver extends BroadcastReceiver {

	private static final String TAG = "WeatherReceiver";
	Context mContext = null;
//	private SystemWriteManager sw;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "==== BootReceiver , action : " + intent.getAction());
		mContext = context;
//		sw = (SystemWriteManager) mContext.getSystemService("system_write");
        String action = intent.getAction();
		if ("android.intent.action.BOOT_COMPLETED".equals(action)) {			
    }
    

        //=================for weather 
		if ("android.windInside.launcher.REQUEST_WEATHER".equals(action)) {
			LogUtils.d("===== receive REQUEST_WEATHER ");
			new WeatherBroadcastThread(mContext).start();
		}

		if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
//            String value = sw.getPropertyString("sys.weather.send", "false");
//            if("false".equals(value)){
			      upDateWeather();
//            }
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
			LogUtils.d("wifi connect , send weather info right now !!!");
			new WeatherBroadcastThread(mContext).start();
		}

	}


}
