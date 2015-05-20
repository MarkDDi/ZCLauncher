package com.word.wordinsidehome.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.widget.Toast;
import android.content.Context;
import java.io.InputStream;
import java.io.IOException;
import android.util.Log;
import java.io.File;
import java.io.FileReader;

public class HttpDownloader {

	/**
	 * ����URL�����ļ���ǰ��������ļ����е��������ı��������ķ���ֵ�����ļ����е�����
	 * 1.����һ��URL����
	 * 2.ͨ��URL���󣬴���һ��HttpURLConnection����
	 * 3.�õ�InputStram
	 * 4.��InputStream���ж�ȡ����
	 * @param urlStr
	 * @return
	 */
	private static final String TAG = "HttpDownloader";
    private Context mContext = null;
    public HttpDownloader(Context context){ 
		    mContext = context;
		    }
    public HttpDownloader(){ 

		}		    
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// ����һ��URL����
			URL url = new URL(urlStr);
			// ����һ��Http����
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// ʹ��IO����ȡ����
			buffer = new BufferedReader(new InputStreamReader(urlConn
					.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}


}
