package com.word.wordinsidehome.service.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.word.wordinsidehome.AppStoreApplication;
import com.word.wordinsidehome.utils.MD5Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImageLoader {
    public static final String TAG = "ImageLoader";
    private static volatile ImageLoader instance;
    private ImageLoaderEngine engine = new ImageLoaderEngine();

    protected ImageLoader() {
        super();
    }

    public synchronized static ImageLoader getInstance() {
        if (ImageLoader.instance == null) {

            try {
                if (ImageLoader.instance == null) {
                    ImageLoader.instance = new ImageLoader();
                }

            } catch (Throwable v0) {

            }
        }

        return ImageLoader.instance;
    }

    public void displayImage(String uri, ImageView imageView, boolean isRefleshData) {
        //	imageView.setImageResource(R.drawable.matrix_recommend_movie);
  /*     LoadAndDisplayImageTask loadthread = new LoadAndDisplayImageTask(uri, imageView); 
       this.engine.submit(loadthread);   */
        download(uri, imageView, isRefleshData);
        Log.d("zzktag", "displayImage() uri=" + uri);

    }

    private static final Executor executor = Executors.newFixedThreadPool(10);
    ;

    @SuppressLint("NewApi")
    public void download(String url, ImageView imageView, boolean isRefleshData) {

        BitmapDownloaderTask task = new BitmapDownloaderTask(imageView, isRefleshData);
        // task.execute(url);
        task.executeOnExecutor(executor, url);
    }


    @SuppressLint("NewApi")
    public class BitmapDownloaderTask extends AsyncTask<String, Void, Uri> {

        private ImageView imageView;
        private boolean isRefleshData;

        public BitmapDownloaderTask(ImageView imageView, boolean isRefleshData) {
            this.imageView = imageView;
            this.isRefleshData = isRefleshData;
        }


        @Override
        protected Uri doInBackground(String... params) {
            Uri imgurl = null;
            imgurl = getImageFileInDiscCache(AppStoreApplication.getAppContext(), params[0], isRefleshData);

            return imgurl;
        }

        @Override
        protected void onPostExecute(Uri imgurl) {
            if (isCancelled()) {
                Log.d(TAG, " onPostExecute....isCancelled imgurl=" + imgurl);
                imgurl = null;
            }
            if (imgurl != null) {
                imageView.setImageURI(imgurl);
                Log.d(TAG, " onPostExecute....imgurl =  " + imgurl);
            }
        }
    }

    private Drawable loadImageFromUrl(String uri) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(new URL(uri).openStream(), "src");
        } catch (IOException e) {
            //throw new RuntimeException();
            e.printStackTrace();
        }
        Log.d(TAG, " loadImageFromUrl....drawable =  " + drawable);
        return drawable;
    }


    private Uri getImageFileInDiscCache(Context mContext, String uri, boolean isRefleshData) {
        Uri imgUrl = null;
        File imgCacheDir = StorageUtils.getCacheDirectory(mContext, true);
        String imgName = MD5Utils.getMD5String(uri);
        Log.d(TAG, " imgName_Md5 =  " + imgName);
        File currentImgFile = new File(imgCacheDir, imgName);

        if (!currentImgFile.exists()) {
            Log.d(TAG, " not exist currentImgFile... =  " + currentImgFile);

       /*     try {
                currentImgFile.createNewFile();
            }
            catch(IOException e) {

            }*/
        }
        Log.d(TAG, " exist currentImgFile... =  " + currentImgFile);
        try {
            imgUrl = tryGetImageURI(currentImgFile, uri, isRefleshData);

        } catch (IOException e) {
            Log.d(TAG, "IOException e  =  " + e);
        }
        return imgUrl;
    }

    /**
     *
     * @param targetFile MD5加密后的缓存文件名
     * @param url 图片url
     * @param isRefleshData 是否刷新数据，第一次为false
     * @return
     * @throws IOException
     */
    private Uri tryGetImageURI(File targetFile, String url, boolean isRefleshData) throws IOException {
        if (targetFile.exists() && !isRefleshData) {
            return Uri.fromFile(targetFile);

        } else {

            URL urlConn = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlConn.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(targetFile);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();

                fos.close();
                return Uri.fromFile(targetFile);
            }
        }
        return null;
    }


}
