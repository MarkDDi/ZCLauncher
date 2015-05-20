package com.word.wordinsidehome.service.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.word.wordinsidehome.service.entity.*;
import java.util.ArrayList;

public class LauncherDAO extends BaseDAO {

    private final String TAG = "BaseDAO";
    private Context context;


    public LauncherDAO(Context context) {
        super();
        this.context = context;
    }

    public String createTableString() {
        StringBuffer v0 = new StringBuffer();
        v0.append("create table ");
        v0.append("TABLE_LAUNCHER");
        v0.append("(");
        v0.append("_id integer primary key ,");        
        v0.append("animVersion text,");              
        v0.append("animation text,");
        v0.append("apk text,");      
        v0.append("description text,");
        v0.append("icon text,");
        v0.append("launcherID integer,");
        v0.append("online integer,");
        v0.append("onlineMsg text,");   
        v0.append("updateDate text,");                                                                                        
        v0.append("version text");
        v0.append(");");
        Log.d(this.TAG, new StringBuilder(String.valueOf(v0.toString())).toString());
        return v0.toString();
    }

    public synchronized void delete(String where, String[] selectionArgs) {

        try {
            this.context.getContentResolver().delete(LauncherDAO.URI_LAUNCHER, where, selectionArgs);
            return;

        }
        catch(Throwable v1) {

        }

    }

    public String dropTable() {
        StringBuffer v0 = new StringBuffer();
        v0.append("DROP TABLE IF EXISTS ");
        v0.append("TABLE_LAUNCHER");
        return v0.toString();
    }

    public synchronized  void insert(ArrayList arg0) {


    }
    public void insert(LauncherEntity launcherEntity) {
    	  ContentResolver resolver = this.context.getContentResolver();  	  
        ContentValues cv = new ContentValues();     	
        cv.put("animVersion", launcherEntity.get_animVersion());
        cv.put("animation", launcherEntity.get_animation());                        	
        cv.put("apk", launcherEntity.get_apk());      	
        cv.put("description", launcherEntity.get_description()); 
        cv.put("icon", launcherEntity.get_icon());  
        cv.put("description", launcherEntity.get_description()); 
        cv.put("icon", launcherEntity.get_icon());  
        cv.put("launcherID", Integer.valueOf(launcherEntity.get_launcherID())); 
        cv.put("online", Integer.valueOf(launcherEntity.get_online()));                       
        cv.put("onlineMsg", launcherEntity.get_onlineMsg()); 
        cv.put("updateDate", launcherEntity.get_updateDate());                          	                                  	
        cv.put("version", launcherEntity.get_version());  
        resolver.insert(LauncherDAO.URI_LAUNCHER, cv);                       
    }
    public void insert(WordinsideBaseEntity wdBaseEntity) {
 
    }
    public synchronized ArrayList query(String[] selections, String where, String[] selectionArgs, String sortOrder) {
    	   return null;
    }
    
    public synchronized  LauncherEntity queryLauncherInfo(String[] selections, String where, String[] selectionArgs, String sortOrder) {
        Cursor c = this.context.getContentResolver().query(LauncherDAO.URI_LAUNCHER, selections, 
                where, selectionArgs, sortOrder);
        if(c != null) {
            if(c.getCount() < 1) {
                c.close();
            }
            else {
                if(c.getCount() > 0) {
                    c.moveToFirst();
                

                    LauncherEntity launcherEntity = new LauncherEntity();                     
									  launcherEntity.set_animVersion(c.getString(c.getColumnIndex("animVersion")));
									  launcherEntity.set_animation(c.getString(c.getColumnIndex("animation")));
									  launcherEntity.set_apk(c.getString(c.getColumnIndex("apk")));
									  launcherEntity.set_description(c.getString(c.getColumnIndex("description")));
									  launcherEntity.set_icon(c.getString(c.getColumnIndex("icon")));
									  launcherEntity.set_launcherID(c.getInt(c.getColumnIndex("animVersion")));
									  launcherEntity.set_online(c.getInt(c.getColumnIndex("animVersion")));	
									  launcherEntity.set_onlineMsg(c.getString(c.getColumnIndex("onlineMsg")));
									  launcherEntity.set_updateDate(c.getString(c.getColumnIndex("updateDate")));
									  launcherEntity.set_version(c.getString(c.getColumnIndex("version")));                    
                                                                                               
                    return launcherEntity;
                  
                  }


                c.close();
            }
        }

        return null;
    }

    public void update(String where, String[] selectionArgs) {
    }
}