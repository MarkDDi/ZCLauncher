package com.word.wordinsidehome.service.image;

import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;

public class FileEntityHandler {
    private boolean mStop;

    public FileEntityHandler() {
        super();
        this.mStop = false;
    }

    public Object handleEntity(HttpEntity entity, EntityCallBack callback, String target, boolean isResume) 
            throws IOException {
        FileOutputStream v9;
        Object v11_1 = null;
        if((TextUtils.isEmpty(((CharSequence)target))) || target.trim().length() == 0) {
            v11_1 = null;
        }
        else {
            File v11 = new File(target);
            if(!v11.exists()) {
                v11.createNewFile();
            }

            if(this.mStop) {
                return v11_1;
            }

            long v4 = 0;
            if(isResume) {
                v4 = v11.length();
                v9 = new FileOutputStream(target, true);
            }
            else {
                v9 = new FileOutputStream(target);
            }

            if(this.mStop) {
                return v11_1;
            }

            InputStream v8 = entity.getContent();
            long v2 = entity.getContentLength() + v4;
            if(v4 >= v2) {
                return v11_1;
            }

            if(this.mStop) {
                return v11_1;
            }

            byte[] v7 = new byte[0x400];
            while(!this.mStop) {
                if(v4 >= v2) {
                    break;
                }

                int v10 = v8.read(v7, 0, 0x400);
                if(v10 <= 0) {
                    break;
                }

                v9.write(v7, 0, v10);
                v4 += ((long)v10);
                callback.callBack(v2, v4, false);
            }

            callback.callBack(v2, v4, true);
            if(!this.mStop) {
                return v11_1;
            }

            if(v4 >= v2) {
                return v11_1;
            }

            throw new IOException("user stop download thread");
        }

        return v11_1;
    }

    public boolean isStop() {
        return this.mStop;
    }

    public void setStop(boolean stop) {
        this.mStop = stop;
    }
}

