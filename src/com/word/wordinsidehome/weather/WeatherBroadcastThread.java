package com.word.wordinsidehome.weather;

import java.util.TimerTask;

import android.app.SystemWriteManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 
import com.word.wordinsidehome.utils.NetworkUtils;

import android.util.Log;

public class WeatherBroadcastThread extends Thread {

	private final String TAG = "WeatherBroadcastThread";
	private static final String PREFERENCE_WEATHER_SETTING = "preference_weather_settings";
	private Context mContext = null;
    SystemWriteManager sw = null;

	@Override
	public synchronized void start() {
		super.start();
	}

	public WeatherBroadcastThread(Context context) {
		mContext = context;
        sw = (SystemWriteManager) mContext.getSystemService("system_write");		
	}

	@Override
	public void run() {
		synchronized (PREFERENCE_WEATHER_SETTING) {
			sendWeatherBroadcast();
		}

	}

	void sendWeatherBroadcast() {
		Log.d("zzkw","=====  sendWeatherBroadcast() ");
      
		SharedPreferences sharedPrefrences = mContext.getSharedPreferences(PREFERENCE_WEATHER_SETTING, Context.MODE_PRIVATE);
		String city = sharedPrefrences.getString("settings_city", "***");
		if ("***".equals(city)) {
			
		}

        if(!NetworkUtils.isNetAvailable(mContext)){
            Log.d("zzkw","==== Wifi and Ethernet both are disconnect ");
            return ;
        }
    String mWeatherInfo;
		mWeatherInfo = WebServiceUtil.getWeatherInfo();

		if (mWeatherInfo == null) {
			try {
					Log.d("zzkw","sleep1 30s");
				Thread.sleep(30 * 1000);
			} catch (Exception e) {
			}
			mWeatherInfo = WebServiceUtil.getWeatherInfo();
		}
		if (mWeatherInfo == null) {
			Log.d("zzkw","mWeatherInfo == null return");
			return;
		}
		Log.d("zzkw", "============mWeatherInfo: " + mWeatherInfo.toString());

		String day_icon = null;
		String mTemperatureInfo = null;
	  city ="深圳";
	    try { 
	    	Log.d("zzkw", "============mWeatherInfo: " + mWeatherInfo);
	      JSONObject jsonObj = new JSONObject(mWeatherInfo);
	      JSONObject jsonObjWeatherdata = jsonObj.getJSONObject("weatcher");
	      	     	day_icon = jsonObjWeatherdata.getString("dayPictureUrl");
	      	     	Log.d("zzkw", "===3333== day_icon: "+day_icon);
	            	mTemperatureInfo = jsonObjWeatherdata.getString("temperature");
	    		      Log.d("zzkw", "===3333== mTemperatureInfo  : " + mTemperatureInfo);
		
		  } catch (JSONException e) { 
            Log.d("zzkw","Jsons parse error =",e); 
            e.printStackTrace(); 
      } 

		if (mTemperatureInfo != null && day_icon != null && !city.equals("***")) {
			String today_info = city + "," + mTemperatureInfo + "," + day_icon;
			Log.d("zzkw", "===== today weather info : " + today_info);

			Intent i = new Intent();
			i.setAction("android.amlogic.settings.WEATHER_INFO");
            i.putExtra("weather_today", today_info);
            
			Log.d("zzkw","===== send broadcast with info , key : weather_today" + " ,value : " + today_info);
			mContext.sendBroadcast(i);
            sw.setProperty("sys.weather.send", "true");
		} else {
			Log.d("zzkw", "===== something wrong happen !!!");
		}

	}

}
