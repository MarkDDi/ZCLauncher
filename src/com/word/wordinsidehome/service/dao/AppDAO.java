package com.word.wordinsidehome.service.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.word.wordinsidehome.service.entity.*;
import com.word.wordinsidehome.utils.LogUtils;

import java.util.ArrayList;

public class AppDAO extends BaseDAO {

    private final String TAG = "BaseDAO";
    private Context context;


    public AppDAO(Context context) {
        super();
        this.context = context;
    }

    public String createTableString() {
        StringBuffer v0 = new StringBuffer();
        v0.append("create table ");
        v0.append("TABLE_APP");
        v0.append("(");
        v0.append("_id integer primary key ,");
        v0.append("position integer,");
        v0.append("action text,");
        v0.append("apk text,");
        v0.append("appID integer,");
        v0.append("appName text,");
        v0.append("className text,");
        v0.append("description text,");
        v0.append("icon text,");
        v0.append("packageName text,");
        v0.append("params text,");
        v0.append("startType integer,");
        v0.append("version text");
        v0.append(");");
        Log.d(this.TAG, new StringBuilder(String.valueOf(v0.toString())).toString());
        return v0.toString();
    }

    public synchronized void delete(String where, String[] selectionArgs) {

        try {
            this.context.getContentResolver().delete(AppDAO.URI_APP, where, selectionArgs);
            return;

        } catch (Throwable v1) {

        }

    }

    public String dropTable() {
        StringBuffer v0 = new StringBuffer();
        v0.append("DROP TABLE IF EXISTS ");
        v0.append("TABLE_APP");
        return v0.toString();
    }

    public synchronized void insert(ArrayList arg0) {
        int size = arg0.size();
        for (int i = 0; i < size; ++i) {
            this.insert((IconsEntity) arg0.get(i));
        }

    }

    public void insert(IconsEntity iconsEntity) {
        ContentResolver resolver = this.context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("position", Integer.valueOf(iconsEntity.get_position()));
        cv.put("action", iconsEntity.get_action());
        cv.put("apk", iconsEntity.get_apk());
        cv.put("appID", Integer.valueOf(iconsEntity.get_appID()));
        cv.put("appName", iconsEntity.get_appName());
        cv.put("className", iconsEntity.get_className());
        cv.put("description", iconsEntity.get_description());
        cv.put("icon", iconsEntity.get_icon());
        cv.put("packageName", iconsEntity.get_packageName());
        cv.put("params", iconsEntity.get_params());
        cv.put("startType", iconsEntity.get_startType());
        cv.put("version", iconsEntity.get_version());
        resolver.insert(AppDAO.URI_APP, cv);
    }

    public void insert(WordinsideBaseEntity wdBaseEntity) {
        ArrayList iconsEntityArrayList = ((AppEntity) wdBaseEntity).get_iconsEntity();
        insert(iconsEntityArrayList);
    }


    public synchronized ArrayList query(String[] selections, String where, String[] selectionArgs, String sortOrder) {
        Cursor c = this.context.getContentResolver().query(AppDAO.URI_APP, selections, where, selectionArgs, sortOrder);
        ArrayList<IconsEntity> iconsArray = new ArrayList();
        if (c != null) {
            if (c.getCount() < 1) {
                c.close();
            } else {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                }

                do {
                    IconsEntity icon = new IconsEntity();
                    icon.set_position(c.getInt(c.getColumnIndex("position")));
                    icon.set_action(c.getString(c.getColumnIndex("action")));
                    icon.set_apk(c.getString(c.getColumnIndex("apk")));
                    icon.set_appID(c.getInt(c.getColumnIndex("appID")));
                    icon.set_appName(c.getString(c.getColumnIndex("appName")));
                    icon.set_className(c.getString(c.getColumnIndex("className")));
                    icon.set_description(c.getString(c.getColumnIndex("description")));
                    icon.set_icon(c.getString(c.getColumnIndex("icon")));
                    icon.set_packageName(c.getString(c.getColumnIndex("packageName")));
                    icon.set_params(c.getString(c.getColumnIndex("params")));
                    icon.set_startType(c.getInt(c.getColumnIndex("startType")));
                    icon.set_version(c.getString(c.getColumnIndex("version")));
                    iconsArray.add(icon);
                } while (c.moveToNext());

                c.close();
            }
        }

        return iconsArray;
    }

    public synchronized ArrayList queryMatrixApp(String[] selections, String where, String[] selectionArgs, String sortOrder) {
        ArrayList<ArrayList<IconsEntity>> v0 = new ArrayList();
        try {

            int v1;
            for (v1 = 0; v1 < 6; ++v1) {
                v0.add(this.query(null, new StringBuffer().append("position").append(" = ? ").toString(),
                                        new String[]{new StringBuilder(String.valueOf(v1 + 1)).toString()}, null));
                LogUtils.d("v1=" + v1);
            }

            return v0;


        } catch (Throwable v3) {
            Log.d("zzktag", "" + v3);
        }
        return v0;
    }

    public void update(String where, String[] selectionArgs) {
    }
}

