package com.word.wordinsidehome.service.image;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.word.wordinsidehome.service.entity.*;
import com.word.wordinsidehome.common.DownloadManager;
import java.io.File;
import android.view.*;
import android.widget.Toast;
import com.word.wordinsidehome.AppStoreApplication;
import java.io.Serializable;
import com.word.wordinsidehome.R;

public class AppDownloadThread {
    private static final String TAG = "AppDownloadThread";
    private boolean flag;
    private HttpHandler handler;
    private long progress;
    private String target;
    private IconsEntity tIcons;
    private  Context  mContext;
    public AppDownloadThread(Context context,IconsEntity iconsInfo) {
        super();
        mContext =context;
        this.progress = 0;
        this.tIcons = iconsInfo;
        this.flag = false;
        this.target = FileLoadUtils.getExternalStorageAbsolutePath("dowloadapk.apk", context);
                Log.i(TAG,  "downLoad url == " + this.target);
    }



    public void beginDownloadProgressBroadcast() {
        this.flag = true;
        new Thread(new Runnable() {
            public void run() {
                do {
                    if(!AppDownloadThread.this.flag) {
                        return;
                    }

                    long v1 = 0x3E8; // 1000
                    try {
                        Thread.sleep(v1);
                    }
                    catch(InterruptedException v0) {
                        v0.printStackTrace();
                    }
                      AppStoreApplication.sendDownloadProgressAppAction(AppDownloadThread.this.tIcons, 
                           AppDownloadThread.this.progress);

                }
                while(AppDownloadThread.this.progress != 0x64); // 100

                AppDownloadThread.this.flag = false;
                AppDownloadThread.this.downloadComplete(target,AppDownloadThread.this.tIcons);
            }
        }).start();
    }

    public void download(IconsEntity iconEntry) {
	 final IconsEntity miconEntry = iconEntry;
    	           Log.i(TAG, "app download start");

                File v0 = new File(this.target);
                if((v0.isFile()) && (v0.exists())) {
                    v0.delete();
                }

            this.beginDownloadProgressBroadcast();
			AppStoreApplication.sendDownloadStartAppaction(AppDownloadThread.this.tIcons);			
            ++DownloadManager.currentDownloadSize;
            this.handler = new FinalHttp().download(iconEntry.get_apk(),
                    this.target, true, new AjaxCallBack() {
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                    AppDownloadThread.this.flag = false;
	                      --DownloadManager.currentDownloadSize;
							        try {
							            Thread.sleep(2000);
							        }
							        catch(InterruptedException v0) {
							            v0.printStackTrace();
							        }	                      
					          Toast toast = Toast.makeText(mContext,
					          R.string.app_download_error,
					          Toast.LENGTH_SHORT);
					          toast.setGravity(Gravity.CENTER, 0, 0);
					          toast.show(); 	                      
	                   AppStoreApplication.sendDownloadFailAppAction(miconEntry);               
                    Log.i(TAG, "  download  failure");
                }

                public void onLoading(long count, long current) {
                    AppDownloadThread.this.progress = 0x64 * current / count;
                }

                public void onStart() {
                    super.onStart();
                    Log.i(TAG, "  start");
                }

                public void onSuccess(Object t) {
                    Log.i(TAG,  "  download success");
                    AppDownloadThread.this.flag = false;
                }

                public AjaxCallBack progress(boolean progress, int rate) {
                    return super.progress(progress, rate);
                }
            });
  
    }

    public void downloadComplete(String target,IconsEntity tIcons) {
    	       --DownloadManager.currentDownloadSize;
			 AppStoreApplication.sendDownloadCompleteAppaction(tIcons);				   
          	 Log.i(TAG,  "  downloadComplete");
            // 发送到IntentService中
            Intent intent = new Intent(mContext, AppInstallService.class);
            intent.putExtra("target", target);
			intent.putExtra("AppEntity", ((Serializable)tIcons));
            mContext.startService(intent);    	

    }



    public long getDownloadFileLength() {
        long v0 = 0;
        File v2 = new File(this.target);
        if((v2.isFile()) && (v2.exists())) {
            v0 = v2.length();
        }

        return v0;
    }

    public long getProgress() {
        return this.progress;
    }

    public String getSavePath() {
        return this.target;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public void stopDownloadThread() {
        if(this.handler != null) {
            this.handler.stop();
        }
    }
    
    public  void sendDownloadProgressAppAction(long progress) {
    //    Intent v2 = new Intent("com.hiveview.appstore.download_progress");
    //    v2.putExtra("download_progress", ((int)progress));
     // AppDownloadThread.this.sendBroadcast(v2);
    }
        
}

