package com.word.wordinsidehome.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;

public class StringEntityHandler {
    public StringEntityHandler() {
        super();
    }

    public Object handleEntity(HttpEntity entity, EntityCallBack callback, String charset) throws IOException {
        String v1_1;
        if(entity == null) {
            Object v1 = null;
        }
        else {
            ByteArrayOutputStream v11 = new ByteArrayOutputStream();
            byte[] v7 = new byte[0x400];
            long v2 = entity.getContentLength();
            long v4 = 0;
            InputStream v9 = entity.getContent();
            while(true) {
                int v10 = v9.read(v7);
                if(v10 == 0xFFFFFFFF) {
                    break;
                }

                v11.write(v7, 0, v10);
                v4 += ((long)v10);
                if(callback == null) {
                    continue;
                }

                callback.callBack(v2, v4, false);
            }

            if(callback != null) {
                callback.callBack(v2, v4, true);
            }

            byte[] v8 = v11.toByteArray();
            v11.close();
            v9.close();
            v1_1 = new String(v8, charset);
        }

        return null;
    }
}

