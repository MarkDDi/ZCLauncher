package com.word.wordinsidehome.service.net;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpTaskManager {
    private static HttpTaskManager MANAGER = null;
    private static ExecutorService POOL = null;
    private static final int SIZE = 0xC;

    static {
        HttpTaskManager.POOL = Executors.newFixedThreadPool(0xC);
        HttpTaskManager.MANAGER = new HttpTaskManager();
    }

    public HttpTaskManager() {
        super();
    }

    public boolean awaitTermination(long timeOut, TimeUnit unit) throws InterruptedException {
        return HttpTaskManager.POOL.awaitTermination(timeOut, unit);
    }

    public static HttpTaskManager getInstance() {
        if(HttpTaskManager.MANAGER.isShutdown()) {
            HttpTaskManager.POOL = null;
            HttpTaskManager.POOL = Executors.newFixedThreadPool(0xC);
        }

        return HttpTaskManager.MANAGER;
    }

    public boolean isShutdown() {
        return HttpTaskManager.POOL.isShutdown();
    }

    public boolean isTerminated() {
        return HttpTaskManager.POOL.isTerminated();
    }

    public void shutdown() {
        HttpTaskManager.POOL.shutdown();
    }

    public void submit(Runnable obj) {
        HttpTaskManager.POOL.submit(obj);
    }

    public void submit(Callable arg2) {
        HttpTaskManager.POOL.submit(arg2);
    }
}

