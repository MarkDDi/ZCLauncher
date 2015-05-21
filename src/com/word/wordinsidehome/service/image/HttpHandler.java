package com.word.wordinsidehome.service.image;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

@SuppressLint("NewApi")
public class HttpHandler extends AsyncTask implements EntityCallBack {
    private static final String TAG = "HttpHandler";
    private static final int UPDATE_FAILURE = 3;
    private static final int UPDATE_LOADING = 2;
    private static final int UPDATE_START = 1;
    private static final int UPDATE_SUCCESS = 4;
    private final AjaxCallBack callback;
    private String charset;
    private final AbstractHttpClient client;
    private final HttpContext context;
    private int executionCount;
    private boolean isResume;
    private final FileEntityHandler mFileEntityHandler;
    private final StringEntityHandler mStrEntityHandler;
    private String targetUrl;
    private long time;

    public HttpHandler(AbstractHttpClient client, HttpContext context, AjaxCallBack arg5, String charset) {
        super();
        this.mStrEntityHandler = new StringEntityHandler();
        this.mFileEntityHandler = new FileEntityHandler();
        this.executionCount = 0;
        this.targetUrl = null;
        this.isResume = false;
        this.client = client;
        this.context = context;
        this.callback = arg5;
        this.charset = charset;
    }

    public void callBack(long count, long current, boolean mustNoticeUI) {
        Object[] v2;
        int v9 = 3;
        int v6 = 2;
        if(this.callback != null && (this.callback.isProgress())) {
            if(mustNoticeUI) {
                v2 = new Object[v9];
                v2[0] = Integer.valueOf(v6);
                v2[1] = Long.valueOf(count);
                v2[v6] = Long.valueOf(current);
                this.publishProgress(v2);
            }
            else {
                long v0 = SystemClock.uptimeMillis();
                if(v0 - this.time >= (((long)this.callback.getRate()))) {
                    this.time = v0;
                    v2 = new Object[v9];
                    v2[0] = Integer.valueOf(v6);
                    v2[1] = Long.valueOf(count);
                    v2[v6] = Long.valueOf(current);
                    this.publishProgress(v2);
                }
            }
        }
    }

    protected Object doInBackground(Object[] params) {
        int v7 = 2;
        int v6 = 3;
        if(params != null && params.length == v6) {
            this.targetUrl = String.valueOf(params[1]);

        }

        try {
            this.publishProgress(new Object[]{Integer.valueOf(1)});
            this.makeRequestWithRetries((HttpUriRequest)params[0]);
        }
        catch(IOException v0) {
            Object[] v1 = new Object[4];
            v1[0] = Integer.valueOf(v6);
            v1[1] = v0;
            v1[v7] = Integer.valueOf(0);
            v1[v6] = v0.getMessage();
            this.publishProgress(v1);
        }

        return null;
    }

    private void handleResponse(HttpResponse response) {
        Object[] v5;
        int v12 = 4;
        int v11 = 2;
        int v9 = 3;
        StatusLine v4 = response.getStatusLine();
        if(v4.getStatusCode() >= 0x12C) {
            String v2 = "response status error code:" + v4.getStatusCode();
            if(v4.getStatusCode() == 0x1A0 && (this.isResume)) {
                v2 = String.valueOf(v2) + " \n maybe you have download complete.";
            }

            v5 = new Object[v12];
            v5[0] = Integer.valueOf(v9);
            v5[1] = new HttpResponseException(v4.getStatusCode(), v4.getReasonPhrase());
            v5[v11] = Integer.valueOf(v4.getStatusCode());
            v5[v9] = v2;
            this.publishProgress(v5);
            return;
        }

        try {
            HttpEntity v1 = response.getEntity();
            Object v3 = null;
            if(v1 != null) {
                this.time = SystemClock.uptimeMillis();
                v3 = this.targetUrl != null ? this.mFileEntityHandler.handleEntity(v1, ((EntityCallBack)
                        this), this.targetUrl, this.isResume) : this.mStrEntityHandler.handleEntity(
                        v1, ((EntityCallBack)this), this.charset);
            }

            this.publishProgress(new Object[]{Integer.valueOf(4), v3});
        }
        catch(IOException v0) {
            v5 = new Object[v12];
            v5[0] = Integer.valueOf(v9);
            v5[1] = v0;
            v5[v11] = Integer.valueOf(0);
            v5[v9] = v0.getMessage();
            this.publishProgress(v5);
        }
    }

    public boolean isStop() {
        return this.mFileEntityHandler.isStop();
    }

    private void makeRequestWithRetries(HttpUriRequest request) throws IOException {
        int v8_1;
        int v12 = 3;
        if((this.isResume) && this.targetUrl != null) {
            File v1 = new File(this.targetUrl);
            long v3 = 0;
            if((v1.isFile()) && (v1.exists())) {
                v3 = v1.length();
            }

            if(v3 > 0) {
                Log.i("HttpHandler", "fileLen ---" + v3);
            }

            request.setHeader("RANGE", "bytes=" + v3 + "-");
        }

        boolean v6 = true;
        IOException v0 = null;
        HttpRequestRetryHandler v7 = this.client.getHttpRequestRetryHandler();
        while(true) {
            if(!v6) {
				        if(v0 != null) {
				            throw v0;
				        }
				
				        throw new IOException("未知网络错误");
            }

            try {
                if(this.isCancelled()) {
                    return;
                }

                HttpResponse v5 = this.client.execute(request, this.context);
                if(this.isCancelled()) {
                    return;
                }

                this.handleResponse(v5);
                return;
            }
            catch(Exception v2) {
                v0 = new IOException("Exception" + v2.getMessage());
                v8_1 = this.executionCount + 1;
                this.executionCount = v8_1;
                v6 = v7.retryRequest(v0, v8_1, this.context);
                continue;
            }


        }




    }

    protected void onProgressUpdate(Object[] values) {
        int v4 = 2;
        switch(Integer.valueOf(String.valueOf(values[0])).intValue()) {
            case 1: {
                if(this.callback == null) {
                         super.onProgressUpdate(values);
                }

                this.callback.onStart();
                break;
            }
            case 2: {
                if(this.callback == null) {
                         super.onProgressUpdate(values);
                }

                this.callback.onLoading(Long.valueOf(String.valueOf(values[1])).longValue(), Long.valueOf(
                        String.valueOf(values[v4])).longValue());
                break;
            }
            case 3: {
                if(this.callback == null) {
                          super.onProgressUpdate(values);
                }

                this.callback.onFailure((Throwable)values[1], Integer.valueOf(String.valueOf(values[v4])).intValue(), String.valueOf(values[3]));
                break;
            }
            case 4: {
                if(this.callback == null) {
                    super.onProgressUpdate(values);
                }

                this.callback.onSuccess(values[1]);
                break;
            }
        }


        super.onProgressUpdate(values);
    }

    public void stop() {
        this.mFileEntityHandler.setStop(true);
    }
}

