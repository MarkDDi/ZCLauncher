package com.word.wordinsidehome.service.image;

import android.annotation.SuppressLint;
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

@SuppressLint("NewApi")
public class AppInstallService extends IntentService {
    private static final String TAG = "AppInstallService";
    private String target;
    private IconsEntity appEntity;

    public AppInstallService() {
        super("AppInstallService");
        Log.d("AppDownloadThread", "AppInstallService---Constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 由AppDownloadThread类中下载完成方法downloadComplete()来开始服务
        this.target = intent.getStringExtra("target");
        appEntity = (IconsEntity) intent.getSerializableExtra("AppEntity");

        Log.i("AppDownloadThread", "onStartCommand -- target=" + this.target);
        sendBeginInstallAppAction();
        installApp(this.target);
    }

    private void installApp(String filePath) {
        Log.i("AppDownloadThread", "filePath == " + filePath);
        int v6 = 1;
        try {
            InputStream in = Runtime.getRuntime().exec("pm install -r " + filePath + " \n").getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            do {
                String line = br.readLine();
                if (line != null) {
                    Log.i("AppDownloadThread", "line == " + line);
                    if (line.indexOf("Success") == 0xFFFFFFFF) { // -1
                        continue;
                    }

                    break;
                }

                if (v6 == 1) {
                    installFail(filePath);
                }

                return;
            } while (true);

            v6 = 0;

            sendInstallSuccessAppAction();
            if (in != null) {
                in.close();
            }

            br.close();


            //  deleteDownloadFile(filePath);

            return;
        } catch (Exception v1) {
            v1.printStackTrace();
            sendInstallFallAppAction();
            installFail(filePath);
            return;
        }
    }

    // 下列的安装以及下载状态广播由MyDialog和LauncherUpdateDialog进行接收
    private void installFail(String filePath) {
        Log.i("AppDownloadThread", "installFail failed");
        sendInstallFallAppAction();
        deleteDownloadFile(filePath);
    }

    private void deleteDownloadFile(String filePath) {
        File v0 = new File(filePath);
        if (v0.exists()) {
            Log.i("AppDownloadThread", "delete =" + filePath + "success");
            v0.delete();
        }
    }

    private void sendInstallSuccessAppAction() {
        Intent v0 = new Intent("com.hiveview.appstore.home_install_success");
        v0.putExtra("AppEntity", ((Serializable) appEntity));
        AppInstallService.this.sendBroadcast(v0);
    }

    private void sendInstallFallAppAction() {
        Intent v0 = new Intent("com.hiveview.appstore.home_install_fail");
        v0.putExtra("AppEntity", ((Serializable) appEntity));
        AppInstallService.this.sendBroadcast(v0);
    }

    private void sendBeginInstallAppAction() {
        Intent v0 = new Intent("com.hiveview.appstore.home_install_begin");
        v0.putExtra("AppEntity", ((Serializable) appEntity));
        AppInstallService.this.sendBroadcast(v0);
    }
}

