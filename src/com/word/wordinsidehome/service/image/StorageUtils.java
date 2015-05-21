package com.word.wordinsidehome.service.image;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import java.io.File;
import java.io.IOException;

public final class StorageUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String INDIVIDUAL_DIR_NAME = "uil-images";

    private StorageUtils() {
        super();
    }

    public static File getCacheDirectory(Context context) {
        return StorageUtils.getCacheDirectory(context, true);
    }

    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File v0 = null;
        if((preferExternal) && ("mounted".equals(Environment.getExternalStorageState())) && (StorageUtils
                .hasExternalStoragePermission(context))) {
            v0 = StorageUtils.getExternalCacheDir(context);
        }

        if(v0 == null) {
            v0 = context.getCacheDir();
        }

        if(v0 == null) {
            String v1 = "/data/data/" + context.getPackageName() + "/cache/";
          //  L.w("Can\'t define system cache directory! \'%s\' will be used.", new Object[]{v1});
            v0 = new File(v1);
        }

        return v0;
    }

    private static File getExternalCacheDir(Context context) {
        File v0 = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), 
                "data"), context.getPackageName()), "cache");
        Environment.getExternalStorageDirectory().toString();
        if(!v0.exists()) {
            if(!v0.mkdirs()) {
            //    L.w("Unable to create external cache directory", new Object[0]);
                return null;
            }

            try {
                new File(v0, ".nomedia").createNewFile();
            }
            catch(IOException v2) {
             //   L.i("Can\'t create \".nomedia\" file in application external cache directory", new Object[0]);
            }
        }

        return v0;
    }

    public static File getIndividualCacheDirectory(Context context) {
        File v0 = StorageUtils.getCacheDirectory(context);
        File v1 = new File(v0, "uil-images");
        if(!v1.exists() && !v1.mkdir()) {
            v1 = v0;
        }

        return v1;
    }

    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File v0 = null;
        if(("mounted".equals(Environment.getExternalStorageState())) && (StorageUtils.hasExternalStoragePermission(
                context))) {
            v0 = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }

        if(v0 == null || !v0.exists() && !v0.mkdirs()) {
            v0 = context.getCacheDir();
        }

        return v0;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        boolean v1 = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                 == PackageManager.PERMISSION_GRANTED ? true : false;
        return v1;
    }
}

