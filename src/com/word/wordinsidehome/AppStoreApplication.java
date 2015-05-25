package com.word.wordinsidehome;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.word.wordinsidehome.common.DownloadManager;
import com.word.wordinsidehome.service.entity.IconsEntity;
import com.word.wordinsidehome.service.image.AppDownloadThread;
import com.word.wordinsidehome.utils.NetworkUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class AppStoreApplication extends Application {
    private static final String TAG = "AppStoreApplication";
    private static HashMap mAppMap;
    private static Context mContext;
    public static DownloadManager mDownloadManager;
    private static List packageFilterList;
    private static List uninstallFilterList;

    public AppStoreApplication() {
        super();
    }


    public static void beginNewDownloadThread(IconsEntity appEntity) {
        //  VersionEntity v0 = appEntity.getVersion();
        if (!NetworkUtils.isNetAvailable(AppStoreApplication.mContext)) {
            //ToastUtils.alert(AppStoreApplication.mContext, 0x7F070044);
            Toast toast = Toast.makeText(AppStoreApplication.mContext, R.string.error_net, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (DownloadManager.currentDownloadSize >= 1) {
            sendBusyDownload(appEntity);

        } else {

            AppDownloadThread loader = new AppDownloadThread(AppStoreApplication.mContext, appEntity);
            //   AppStoreApplication.mDownloadManager.add(v0.getVersion_url(), loader);
            loader.download(appEntity);
        }
    }


    public static Context getAppContext() {
        return AppStoreApplication.mContext;
    }


    public void onCreate() {
        super.onCreate();
        AppStoreApplication.mContext = this.getBaseContext();
        AppStoreApplication.mDownloadManager = new DownloadManager();

    }


    // 下面的广播都是发送到view包的MyDialog中动态广播进行接收
    public static void sendDownloadProgressAppAction(IconsEntity appEntity, long progress) {
        Intent intent = new Intent("com.hiveview.appstore.download_progress");
        intent.putExtra("AppEntity", ((Serializable) appEntity));
        intent.putExtra("download_progress", ((int) progress));
        AppStoreApplication.mContext.sendBroadcast(intent);
    }


    public static void sendDownloadStartAppaction(IconsEntity appEntity) {
        Intent intent = new Intent("com.hiveview.appstore.home.download_start");
        intent.putExtra("AppEntity", ((Serializable) appEntity));
        AppStoreApplication.mContext.sendBroadcast(intent);
    }

    public static void sendDownloadCompleteAppaction(IconsEntity appEntity) {
        Intent intent = new Intent("com.hiveview.appstore.home.download_complete");
        intent.putExtra("AppEntity", ((Serializable) appEntity));
        AppStoreApplication.mContext.sendBroadcast(intent);
    }

    public static void sendDownloadFailAppAction(IconsEntity appEntity) {
        Intent intent = new Intent("com.hiveview.appstore.download_failed");
        intent.putExtra("AppEntity", ((Serializable) appEntity));
        AppStoreApplication.mContext.sendBroadcast(intent);
    }

    public static void sendBusyDownload(IconsEntity appEntity) {
        Intent intent = new Intent("com.hiveview.appstore.download_busy");
        intent.putExtra("AppEntity", ((Serializable) appEntity));
        AppStoreApplication.mContext.sendBroadcast(intent);
    }

}

