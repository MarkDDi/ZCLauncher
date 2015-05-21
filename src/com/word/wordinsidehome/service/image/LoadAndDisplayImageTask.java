package com.word.wordinsidehome.service.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.net.MalformedURLException;
import android.os.Handler;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import android.widget.ImageView;
import android.util.Log;
final class LoadAndDisplayImageTask implements  Runnable {
    class TaskCancelledException extends Exception {
        }
  
    final ImageView imageView;
    final String uri;

    public LoadAndDisplayImageTask(String uri, ImageView imageView) {
        super();
        this.uri = uri;
        this.imageView = imageView;
    }
    
    private Bitmap decodeImage(String imageUri,ImageView imageView) throws IOException {
    	  Log.d("zzktag","decodeImage() bingin");
    	  URL imgUrl = null;
				Bitmap bitmap = null;
				try {
					imgUrl = new URL(imageUri);
					HttpURLConnection urlConn = (HttpURLConnection) imgUrl
							.openConnection();
					urlConn.setDoInput(true);
					urlConn.connect();
					InputStream is = urlConn.getInputStream();
					bitmap = BitmapFactory.decodeStream(is);
					is.close();
				} catch (MalformedURLException e) {
					Log.d("zzktag","[getNetWorkBitmap->]MalformedURLException");
					e.printStackTrace();
				} catch (IOException e) {
					Log.d("zzktag","[getNetWorkBitmap->]IOException");
					e.printStackTrace();
				}
				//imageView.setImageBitmap(bitmap);
		 return bitmap;
	}


    String getLoadingUri() {
        return this.uri;
    }


    public void run() {
        Bitmap v0_1;


        try {
             v0_1 = this.tryLoadBitmap();
             if(v0_1 == null) {
                  return;
              }

        }
        catch(TaskCancelledException v2) {

            return;
        }

    }

    static void runTask(Runnable r, boolean sync, Handler handler) {
        if(sync) {
            r.run();
        }
        else if(handler == null) {
            new Thread(r).start();
        }
        else {
            handler.post(r);
        }
    }



    private Bitmap tryLoadBitmap() throws TaskCancelledException {
        Bitmap v0 = null;
        try {

            v0 = this.decodeImage(this.uri,this.imageView);

        }
        catch(IOException v2) {

        }


        return v0;
    }

}

