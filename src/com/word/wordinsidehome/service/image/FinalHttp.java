package com.word.wordinsidehome.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

public class FinalHttp {
    class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        public long getContentLength() {
            return -1;
        }
    }

    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 0x800;
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private String charset;
    private final Map clientHeaderMap;
    private static final Executor executor = Executors.newFixedThreadPool(3);;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
    private static int httpThreadCount;
    private static int maxConnections;
    private static int maxRetries;

    private static int socketTimeout;

    static {
        FinalHttp.maxConnections = 0xA;
        FinalHttp.socketTimeout = 0x2710;
        FinalHttp.maxRetries = 5;
        FinalHttp.httpThreadCount = 3;

    }

    public FinalHttp() {
        super();
        this.charset = "utf-8";
        BasicHttpParams v1 = new BasicHttpParams();
        ConnManagerParams.setTimeout(((HttpParams)v1), ((long)FinalHttp.socketTimeout));
        ConnManagerParams.setMaxConnectionsPerRoute(((HttpParams)v1), new ConnPerRouteBean(FinalHttp
                .maxConnections));
        ConnManagerParams.setMaxTotalConnections(((HttpParams)v1), 0xA);
        HttpConnectionParams.setSoTimeout(((HttpParams)v1), FinalHttp.socketTimeout);
        HttpConnectionParams.setConnectionTimeout(((HttpParams)v1), FinalHttp.socketTimeout);
        HttpConnectionParams.setTcpNoDelay(((HttpParams)v1), true);
        HttpConnectionParams.setSocketBufferSize(((HttpParams)v1), 0x800);
        HttpProtocolParams.setVersion(((HttpParams)v1), HttpVersion.HTTP_1_1);
        SchemeRegistry v2 = new SchemeRegistry();
        v2.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 0x50));
        v2.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 0x1BB));
        ThreadSafeClientConnManager v0 = new ThreadSafeClientConnManager(((HttpParams)v1), v2);
        this.httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        this.httpClient = new DefaultHttpClient(((ClientConnectionManager)v0), ((HttpParams)v1));
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                if(!request.containsHeader("Accept-Encoding")) {

                }

                Iterator v2 = FinalHttp.this.clientHeaderMap.keySet().iterator();
                while(v2.hasNext()) {
                    Object v0 = v2.next();

                }
            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {
                HttpEntity v2 = response.getEntity();
                if(v2 != null) {
                    Header v1 = v2.getContentEncoding();
                    if(v1 != null) {
                        HeaderElement[] v4 = v1.getElements();
                        int v5 = v4.length;
                        int v3 = 0;
                        while(v3 < v5) {
                            if(v4[v3].getName().equalsIgnoreCase("gzip")) {
                                response.setEntity(new InflatingEntity(response.getEntity()));
                            }
                            else {
                                ++v3;
                                continue;
                            }

                            return;
                        }
                    }
                }
            }
        });
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(FinalHttp.maxRetries));
        this.clientHeaderMap = new HashMap();
    }









    public HttpHandler download(String url, String target, boolean isResume, AjaxCallBack 
            arg11) {
        HttpGet v0 = new HttpGet(url);
        HttpHandler v1 = new HttpHandler(this.httpClient, this.httpContext, arg11, this.charset);
        v1.executeOnExecutor(FinalHttp.executor, new Object[]{v0, target,isResume});
        return v1;
    }





    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }




}

