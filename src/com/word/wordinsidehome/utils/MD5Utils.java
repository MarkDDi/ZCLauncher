package com.word.wordinsidehome.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    protected static char[] hexDigits;
    protected static MessageDigest messageDigest;

    static {
        MD5Utils.hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 
                'c', 'd', 'e', 'f'};
        MD5Utils.messageDigest = null;
        try {
            MD5Utils.messageDigest = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException v0) {
            System.err.println(String.valueOf(MD5Utils.class.getName()) + "初始化失败，MessageDigest不支持MD5!");
            v0.printStackTrace();
        }
    }

    public MD5Utils() {
        super();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char v0 = MD5Utils.hexDigits[(bt & 0xF0) >> 4];
        char v1 = MD5Utils.hexDigits[bt & 0xF];
        stringbuffer.append(v0);
        stringbuffer.append(v1);
    }

    private static String bufferToHex(byte[] bytes) {
        return MD5Utils.bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer v2 = new StringBuffer(n * 2);
        int v0 = m + n;
        int v1;
        for(v1 = m; v1 < v0; ++v1) {
            MD5Utils.appendHexPair(bytes[v1], v2);
        }

        return v2.toString();
    }

    public static boolean checkPassword(String password, String md5PwdStr) {
        return MD5Utils.getMD5String(password).equals(md5PwdStr);
    }

    public static String getFileMD5String(File file) throws IOException {
        String v2;
        FileChannel v1 = new FileInputStream(file).getChannel();
        int v10 = 0x29B92700;
        long v11 = 0;
        long v13 = file.length() / (((long)v10));
        if(v13 == 0) {
            MD5Utils.messageDigest.update(v1.map(FileChannel.MapMode.READ_ONLY, 0, file.length()));
            v2 = MD5Utils.bufferToHex(MD5Utils.messageDigest.digest());
        }
        else {
            int v8 = 0;
            long v3 = v11;
            while((((long)v8)) < v13) {
                MD5Utils.messageDigest.update(v1.map(FileChannel.MapMode.READ_ONLY, v3, ((long)v10)));
                v3 += ((long)v10);
                ++v8;
            }

            if(v3 == file.length()) {
                v2 = MD5Utils.bufferToHex(MD5Utils.messageDigest.digest());
            }
            else {
                MD5Utils.messageDigest.update(v1.map(FileChannel.MapMode.READ_ONLY, v3, file.length()
                         - v3));
                v2 = MD5Utils.bufferToHex(MD5Utils.messageDigest.digest());
            }
        }

        return v2;
    }

    public static String getMD5String(String s) {
        return MD5Utils.getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        MD5Utils.messageDigest.update(bytes);
        return MD5Utils.bufferToHex(MD5Utils.messageDigest.digest());
    }

    public static void main(String[] args) throws IOException {
        System.out.println("md5:" + MD5Utils.getFileMD5String(new File("文件绝对路径")) + " time:" + (System
                .currentTimeMillis() - System.currentTimeMillis()) / 0x3E8 + "s");
    }
}

