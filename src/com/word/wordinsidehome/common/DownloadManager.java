package com.word.wordinsidehome.common;

import java.util.HashMap;
import java.util.Iterator;
import com.word.wordinsidehome.service.image.AppDownloadThread;

public class DownloadManager {
    public static final int DOWNLAOD_SIZE = 3;
    private static final String TAG = "DownloadManager";
    public static int currentDownloadSize;
    private HashMap mapAppDownloadThread;

    static {
        DownloadManager.currentDownloadSize = 0;
    }

    public DownloadManager() {
        super();
        this.mapAppDownloadThread = new HashMap();
    }

    public void add(String url, AppDownloadThread appDownloadThread) {
        this.mapAppDownloadThread.put(url, appDownloadThread);
    }

    public AppDownloadThread getAppDownloadThread(String url) {
        //return this.mapAppDownloadThread.get(url);
        return null;
    }

    public HashMap getListDownloadMainThread() {
        return this.mapAppDownloadThread;
    }

    public void pauseRunnable() {
        Iterator v2 = this.mapAppDownloadThread.keySet().iterator();
        while(v2.hasNext()) {
            ((AppDownloadThread)this.mapAppDownloadThread.get(v2.next())).stopDownloadThread();
        }

        DownloadManager.currentDownloadSize = 0;
    }

    public void remove(String url) {
        this.mapAppDownloadThread.remove(url);
    }
}

