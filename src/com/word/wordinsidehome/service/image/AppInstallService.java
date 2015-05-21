package com.word.wordinsidehome.service.image;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.word.wordinsidehome.service.entity.*;
import java.io.Serializable;
public class AppInstallService extends IntentService {
    private static final String TAG = "AppInstallService";
    private String target;
    private IconsEntity appEntity;
    public AppInstallService() {
        super("AppInstallService");
        Log.d("AppDownloadThread", "AppInstallService---Constructor");
    }

    protected void onHandleIntent(Intent intent) {
        this.target = intent.getStringExtra("target");
		 appEntity = (IconsEntity)intent.getSerializableExtra("AppEntity");

        Log.i("AppDownloadThread", "onStartCommand -- target=" + this.target);
        sendBeginInstallAppAction();
        installApp(this.target);
    }
    
  private  void installApp(String filePath) {
        Log.i("AppDownloadThread", "filePath == " + filePath);
        int v6 = 1;
        try {
            InputStream v3 = Runtime.getRuntime().exec("pm install -r " + filePath + " \n").getInputStream();
            BufferedReader v0 = new BufferedReader(new InputStreamReader(v3));
            do {
                String v4 = v0.readLine();
                if(v4 != null) {
                    Log.i("AppDownloadThread", "line == " + v4);
                    if(v4.indexOf("Success") == 0xFFFFFFFF) {
                        continue;
                    }

                    break;
                }

				          if(v6 == 1) {
				              installFail(filePath);
				          }
				
				          return;
            }
            while(true);

            v6 = 0;

            sendInstallSuccessAppAction();
            if(v3 != null) {
                v3.close();
            }

            v0.close();
 

          //  deleteDownloadFile(filePath);

            return;
        }
        catch(Exception v1) {
            v1.printStackTrace();
            sendInstallFallAppAction();
            installFail(filePath);
            return;
        }
    }

    private void installFail(String filePath) {
    	    Log.i("AppDownloadThread", "installFail failed");
    	   sendInstallFallAppAction();
         deleteDownloadFile(filePath);
    }  
    private  void deleteDownloadFile(String filePath) {
        File v0 = new File(filePath);
        if(v0.exists()) {
        	    	    Log.i("AppDownloadThread", "delete ="+filePath + "success");
            v0.delete();
        }
    }
    
    private  void sendInstallSuccessAppAction() {
        Intent v0 = new Intent("com.hiveview.appstore.home_install_success");
		v0.putExtra("AppEntity", ((Serializable)appEntity));		
        AppInstallService.this.sendBroadcast(v0);
    } 
    private  void sendInstallFallAppAction() {
        Intent v0 = new Intent("com.hiveview.appstore.home_install_fail");
		v0.putExtra("AppEntity", ((Serializable)appEntity));		
        AppInstallService.this.sendBroadcast(v0);
    } 
    private  void sendBeginInstallAppAction() {
        Intent v0 = new Intent("com.hiveview.appstore.home_install_begin");
		v0.putExtra("AppEntity", ((Serializable)appEntity));
        AppInstallService.this.sendBroadcast(v0);
    }                 
}

