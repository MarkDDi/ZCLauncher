package com.word.wordinsidehome.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.word.wordinsidehome.utils.*;
import android.util.Log;
import com.word.wordinsidehome.Launcher;

public class RefleshHandler extends Handler {
    private int delayMillis;
    private boolean isReflesh;
    private Context mContext;

    public RefleshHandler(Context context) {
        super();
        this.isReflesh = false;
        this.delayMillis = 10*60*1000;
        this.mContext = context;
    }

    public void handleMessage(Message msg) {
		Intent intent;
        if(this.isReflesh) {
		switch(msg.what) {
			case 0:
        	   	 Log.d("RefleshHandler","handleMessage()");
            intent = new Intent(mContext, LoadService.class);
            intent.putExtra("isNeedDeviceCheck", false);
            mContext.startService(intent);
            this.sendEmptyMessageDelayed(0, ((long)this.delayMillis));
	        break;
			case 1:
             if(NetworkUtils.isNetAvailable(mContext)){
            intent = new Intent(mContext, LoadService.class);
            intent.putExtra("isNeedDeviceCheck", false);
            mContext.startService(intent);
            sendWeatherBroadcast();
		
			 }else{
			 Log.d("RefleshHandler","no net connect");
             this.sendEmptyMessageDelayed(1, 10000);

			 }
	        break;			
		    }
        }
    }
	private void sendWeatherBroadcast(){
	    Intent intent =new Intent();
	    intent.setAction(Launcher.weather_request_action);
	    mContext.sendBroadcast(intent);
	}	
    public void setDelayMillis(int minute) {
        this.delayMillis = minute * 60 * 1000;
    }

    public void startReflesh() {
        this.isReflesh = true;
        this.sendEmptyMessageDelayed(0, ((long)this.delayMillis));
    }

    public void startRefleshImmediately() {
        this.isReflesh = true;
        this.sendEmptyMessage(0);
        this.sendEmptyMessageDelayed(1, 60000);	
    }

    public void stopReflesh() {
        this.isReflesh = false;
    }
}

