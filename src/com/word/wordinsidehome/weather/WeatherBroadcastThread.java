package com.word.wordinsidehome.weather;

//import android.app.SystemWriteManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.word.wordinsidehome.utils.LogUtils;
import com.word.wordinsidehome.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherBroadcastThread extends Thread {

    private final String TAG = "WeatherBroadcastThread";
    private static final String PREFERENCE_WEATHER_SETTING = "preference_weather_settings";
    private Context mContext = null;
//    SystemWriteManager sw = null;

    @Override
    public synchronized void start() {
        super.start();
    }

    public WeatherBroadcastThread(Context context) {
        mContext = context;
        // 系统内部sdk定制的服务，需要sdk源码才可编译
//        sw = (SystemWriteManager) mContext.getSystemService("system_write");
    }

    @Override
    public void run() {
        synchronized (PREFERENCE_WEATHER_SETTING) {
            sendWeatherBroadcast();
        }

    }

    void sendWeatherBroadcast() {
        LogUtils.d("=====  sendWeatherBroadcast() ");

        SharedPreferences sharedPrefrences = mContext.getSharedPreferences(PREFERENCE_WEATHER_SETTING, Context.MODE_PRIVATE);
        String city = sharedPrefrences.getString("settings_city", "***");
        if ("***".equals(city)) {

        }

        if (!NetworkUtils.isNetAvailable(mContext)) {
            LogUtils.d("==== Wifi and Ethernet both are disconnect ");
            return;
        }
        String mWeatherInfo;
        mWeatherInfo = WebServiceUtil.getWeatherInfo();

        if (mWeatherInfo == null) {
            try {
                LogUtils.d("sleep1 30s");
                Thread.sleep(30 * 1000);
            } catch (Exception e) {
            }
            mWeatherInfo = WebServiceUtil.getWeatherInfo();
        }
        if (mWeatherInfo == null) {
            LogUtils.d("mWeatherInfo == null return");
            return;
        }
        LogUtils.d("============mWeatherInfo: " + mWeatherInfo.toString());

        String day_icon = null;
        String mTemperatureInfo = null;
        city = "深圳";
        try {
            LogUtils.d("============mWeatherInfo: " + mWeatherInfo);
            JSONObject jsonObj = new JSONObject(mWeatherInfo);
            JSONObject jsonObjWeatherdata = jsonObj.getJSONObject("weatcher");
            day_icon = jsonObjWeatherdata.getString("dayPictureUrl");
            LogUtils.d("===3333== day_icon: " + day_icon);
            mTemperatureInfo = jsonObjWeatherdata.getString("temperature");
            LogUtils.d("===3333== mTemperatureInfo  : " + mTemperatureInfo);

        } catch (JSONException e) {
            LogUtils.d("Jsons parse error =", e);
            e.printStackTrace();
        }

        if (mTemperatureInfo != null && day_icon != null && !city.equals("***")) {
            String today_info = city + "," + mTemperatureInfo + "," + day_icon;
            LogUtils.d("===== today weather info : " + today_info);

            Intent i = new Intent();
            i.setAction("android.amlogic.settings.WEATHER_INFO");
            i.putExtra("weather_today", today_info);

            LogUtils.d("===== send broadcast with info , key : weather_today" + " ,value : " + today_info);
            mContext.sendBroadcast(i);
//            sw.setProperty("sys.weather.send", "true");
        } else {
            LogUtils.d("===== something wrong happen !!!");
        }

    }

}
