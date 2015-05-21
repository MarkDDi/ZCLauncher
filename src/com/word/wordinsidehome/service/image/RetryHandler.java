package com.word.wordinsidehome.service.image;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

public class RetryHandler implements HttpRequestRetryHandler {
    private static final int RETRY_SLEEP_TIME_MILLIS = 0x3E8; // 1000
    private static HashSet exceptionBlacklist;
    private static HashSet exceptionWhitelist;
    private final int maxRetries;

    static {
        RetryHandler.exceptionWhitelist = new HashSet();
        RetryHandler.exceptionBlacklist = new HashSet();
        RetryHandler.exceptionWhitelist.add(NoHttpResponseException.class);
        RetryHandler.exceptionWhitelist.add(UnknownHostException.class);
        RetryHandler.exceptionWhitelist.add(SocketException.class);
        RetryHandler.exceptionBlacklist.add(InterruptedIOException.class);
        RetryHandler.exceptionBlacklist.add(SSLHandshakeException.class);
    }

    public RetryHandler(int maxRetries) {
        super();
        this.maxRetries = maxRetries;
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean v2 = true;
        Object v0 = context.getAttribute("http.request_sent");
        int v3 = v0 == null || !((Boolean)v0).booleanValue() ? 0 : 1;
        if(executionCount > this.maxRetries) {
            v2 = false;
        }
        else if(RetryHandler.exceptionBlacklist.contains(exception.getClass())) {
            v2 = false;
        }
        else if(RetryHandler.exceptionWhitelist.contains(exception.getClass())) {
            v2 = true;
        }
        else if(v3 == 0) {
            v2 = true;
        }

        if(v2) {
            Object v1 = context.getAttribute("http.request");
            if(v1 != null && !"POST".equals(((HttpUriRequest)v1).getMethod())) {
                v2 = true;
            }else{
               v2 = false;
            
            }
        }

        if(v2) {
            SystemClock.sleep(0x3E8);
        }
        else {
            exception.printStackTrace();
        }

        return v2;
    }
}

