package com.word.wordinsidehome.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.util.Xml;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List; 
import org.xmlpull.v1.XmlPullParser; 
import android.os.Handler;
import android.os.Message;




public class WebServiceUtil {
	static final String SERVICE_URL = "http://ott.winside.cn:8543/launcher/weather_info.action";


	
	public static String getWeatherInfo() {
		

		HttpDownloader httpDownloader = new HttpDownloader();
		String winfo = httpDownloader.download(SERVICE_URL);
	 	Log.d("zzkw", "getWeatherInfo winfo = " + winfo);
	  return winfo;		
		
		}	
	
}
