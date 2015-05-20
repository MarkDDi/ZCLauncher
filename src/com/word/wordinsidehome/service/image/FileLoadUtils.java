package com.word.wordinsidehome.image;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;
import android.content.Context;

public class FileLoadUtils {
    public static int BUFFER;
    public static char CHARACTER_AND;
    public static char CHARACTER_BARS;
    public static char CHARACTER_EQUAL;
    public static char CHARACTER_QUESTION;
    public static char CHARACTER_SLASH;
    public static String WININSIDEBOXDIR;
    public static int DOWN_THREAD_LENGTH;
    public static int NO_0;
    public static int NO_1;
    public static int NO_10;
    public static int NO_2;
    public static int NO_3;
    public static int NO_4;
    public static int NO_40;
    public static int NO_41;
    public static int NO_42;
    public static int NO_5;
    public static int NO_6;
    public static int NO_7;
    public static int NO_8;
    public static int NO_9;
    public static int NO_END;
    public static int NO_ERROR;
    public static int SECOND_1;
    public static int SECOND_HALF;
    private static long SIZE_100M;

    static {
        FileLoadUtils.SIZE_100M = 0x6400000;
        FileLoadUtils.NO_0 = 0;
        FileLoadUtils.NO_1 = 1;
        FileLoadUtils.NO_2 = 2;
        FileLoadUtils.NO_3 = 3;
        FileLoadUtils.NO_4 = 4;
        FileLoadUtils.NO_5 = 5;
        FileLoadUtils.NO_6 = 6;
        FileLoadUtils.NO_7 = 7;
        FileLoadUtils.NO_8 = 8;
        FileLoadUtils.NO_9 = 9;
        FileLoadUtils.NO_10 = 0xA;
        FileLoadUtils.NO_40 = 0x28;
        FileLoadUtils.NO_41 = 0x29;
        FileLoadUtils.NO_42 = 0x2A;
        FileLoadUtils.NO_ERROR = 0xFFFFFFFF;
        FileLoadUtils.NO_END = 0xFFFFFFFF;
        FileLoadUtils.SECOND_HALF = 0x1F4;
        FileLoadUtils.SECOND_1 = 0x3E8;
        FileLoadUtils.BUFFER = 0x19000;
        FileLoadUtils.DOWN_THREAD_LENGTH = 1;
        FileLoadUtils.CHARACTER_SLASH = '/';
        FileLoadUtils.CHARACTER_EQUAL = '=';
        FileLoadUtils.CHARACTER_AND = '&';
        FileLoadUtils.CHARACTER_QUESTION = '?';
        FileLoadUtils.CHARACTER_BARS = '-';
        FileLoadUtils.WININSIDEBOXDIR = "WordInside/app";
    }

    public FileLoadUtils() {
        super();
    }

    public static long getAvailableExternalStorageSize() {
        StatFs v7 = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long v2 = (((long)v7.getAvailableBlocks())) * (((long)v7.getBlockSize())) - FileLoadUtils.SIZE_100M;
        if(v2 < (((long)FileLoadUtils.NO_0))) {
            v2 = 0;
        }

        return v2;
    }

    public static String getExternalStorageAbsolutePath(String urlStr,Context context) {
        int v2 = urlStr.lastIndexOf(FileLoadUtils.CHARACTER_SLASH);
        if(v2 > 0) {
            urlStr = urlStr.substring(v2 + 1);
        }

        String v0 = FileLoadUtils.getLoadExternalDir(context);
        File v1 = new File(v0);
        if(!v1.exists()) {
            v1.mkdirs();
        }

        return String.valueOf(v0) + urlStr;
    }

    public static String getLoadExternalDir(Context context) {
        String v1 = Environment.getExternalStorageDirectory().getAbsolutePath();
        StringBuffer v0 = new StringBuffer();
        v0.append(v1);
        v0.append(FileLoadUtils.CHARACTER_SLASH);
        v0.append(FileLoadUtils.WININSIDEBOXDIR);
        v0.append(FileLoadUtils.CHARACTER_SLASH);
        return v0.toString();
       // String v1 = "/data/data/" + context.getPackageName() + "/cache/app/";
       // return v1;
    }

    public static boolean isExternalStorageMountAvailable() {
        boolean v0 = "mounted".equals(Environment.getExternalStorageState()) ? true : false;
        return v0;
    }
}

