package com.word.wordinsidehome.weather;

import android.util.Log;




public class WebServiceUtil {
	static final String SERVICE_URL = "http://ott.winside.cn:8543/launcher/weather_info.action";


	
	public static String getWeatherInfo() {
		

		HttpDownloader httpDownloader = new HttpDownloader();
		String winfo = httpDownloader.download(SERVICE_URL);
	 	Log.d("zzkw", "getWeatherInfo winfo = " + winfo);
	  return winfo;		
		
		}	
	
}
