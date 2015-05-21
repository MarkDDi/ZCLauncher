package com.word.wordinsidehome.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.winside.launcher.service.*;
import com.winside.launcher.service.impl.*;
import com.word.wordinsidehome.service.entity.*;
import com.word.wordinsidehome.service.dao.*;
import com.word.wordinsidehome.utils.*;
import com.word.wordinsidehome.service.image.AppDownloadThread;


public class LoadService extends IntentService {
    private static final String TAG = "LoadService";
    public static final String DATA_ACTION = "DATA_ACTION";
    private ArrayList iconsEntityArrayList;	
    private IconsEntity  iconsEntity;
    private GameEntity  gameEntity;
    private HealthEntity  healthEntity;
    private EducationEntity  educationEntity;
    private RecommendEntity  recommendEntity;
    private MovieEntity  movieEntity;
    private AppEntity  appEntity;    
    private LauncherEntity  launcherEntity; 	
    public LoadService() {
        super("LoadService");

    }    	
    private void loadLauncherData() {
    	  Log.d(TAG,"loadLauncherData()");
        iconsEntityArrayList = new ArrayList();
        IconsEntity iconsEntity = new IconsEntity();
   	    LauncherService ls = new LauncherServiceImpl(); 
   	    int blocksNum =0;
   		      try{
   		      	         Log.d(TAG,"loadLauncherData 111()");
              String info  = 	ls.info();
                 	     Log.d(TAG,"loadLauncherData 222()");
            //   Log.d(TAG,"info = "+info);
              JSONObject  dataJson=new JSONObject(info);
              JSONArray  blocksArray  = dataJson.getJSONArray("blocks");
           for(int b= 0 ;b< blocksArray.length();b++){
           	iconsEntityArrayList.clear();
              JSONObject data = blocksArray.getJSONObject(b);
              int blockID = data.getInt("blockID");
              String name = data.getString("name");
               Log.d(TAG,"name ="+name);	
              if(name.equals("游戏")){
                  Log.d(TAG,"正在解析游戏");	
                  blocksNum = 7;
              }else if(name.equals("健康")){
                   Log.d(TAG,"正在解析健康");	             	
              	blocksNum = 8;
              	
              }else if(name.equals("推荐")){
                  Log.d(TAG,"正在解析推荐");	              	
              	blocksNum = 7;
              	
              }else if(name.equals("教育")){
                  Log.d(TAG,"正在解析教育");	              	
              	blocksNum = 8;
              	
              }else if(name.equals("影视")){
                   Log.d(TAG,"正在解析影视");	             	
              	blocksNum = 8;
              	
              }else if(name.equals("应用")){
                   Log.d(TAG,"正在解析应用");	             	
              	blocksNum = 6;
              	
              }else{
              		blocksNum =0;
              	
              }
              JSONObject icons =(JSONObject) data.getJSONObject("icons");
              
             	for(int ii= 0 ;ii < blocksNum;ii++){
             	 String strNum = String.valueOf(ii+1);    
             	          
              	JSONArray arry = icons.getJSONArray(strNum);  
              	for(int j= 0 ;j< arry.length();j++)
              	{
              		JSONObject tmp1 =  arry.getJSONObject(j); 
              		IconsEntity tmpIconsEntity = new IconsEntity();
                  tmpIconsEntity.set_position(ii+1); 
              		tmpIconsEntity.set_action(tmp1.getString("action"));                                		
              		tmpIconsEntity.set_apk(tmp1.getString("apk"));
              		tmpIconsEntity.set_appID(tmp1.getInt("appID"));
              		tmpIconsEntity.set_appName(tmp1.getString("appName"));
              		tmpIconsEntity.set_className(tmp1.getString("className"));
              		tmpIconsEntity.set_description(tmp1.getString("description"));
              		tmpIconsEntity.set_icon(tmp1.getString("icon"));
              		tmpIconsEntity.set_packageName(tmp1.getString("packageName"));   
              		tmpIconsEntity.set_params(tmp1.getString("params")); 
              		tmpIconsEntity.set_startType(tmp1.getInt("startType"));              		
              		tmpIconsEntity.set_version(tmp1.getString("version"));                		             		             		              		              		              		              		
              		Log.d(TAG,"apk = "+tmpIconsEntity.toString());	
              		iconsEntityArrayList.add(tmpIconsEntity);
              	}
               	             
        //      iconsEntity.set_position(ii+1);
              //iconsEntity.set_icons(IconCellArrayList);
             // iconsEntityArrayList.add(tmpIconsEntity);

              Log.d(TAG,"blockID = "+blockID);
             }
             if(name.equals("游戏")){
                    gameEntity = new GameEntity();
                    gameEntity.set_blockID(data.getInt("blockID"));
                    gameEntity.set_iconsEntity(iconsEntityArrayList); 
                    gameEntity.set_name(data.getString("name")); 
                    gameEntity.set_online(data.getBoolean("online")); 
                    gameEntity.set_onlineMsg(data.getString("onlineMsg"));  
                    gameEntity.set_sortID(data.getInt("sortID"));
                    GameDAO gamedao = new GameDAO(this.getApplicationContext());
                    gamedao.delete(null, null);
                    gamedao.insert(gameEntity);   
                    Log.d(TAG,"正在插入游戏到数据库中");	                 
                                                                                                  
              }else if(name.equals("健康")){
                    healthEntity = new HealthEntity();
                    healthEntity.set_blockID(data.getInt("blockID"));
                    healthEntity.set_iconsEntity(iconsEntityArrayList); 
                    healthEntity.set_name(data.getString("name")); 
                    healthEntity.set_online(data.getBoolean("online")); 
                    healthEntity.set_onlineMsg(data.getString("onlineMsg"));  
                    healthEntity.set_sortID(data.getInt("sortID"));
                    HealthDAO healthdao = new HealthDAO(this.getApplicationContext());
                    healthdao.delete(null, null);
                    healthdao.insert(healthEntity);
                    Log.d(TAG,"正在插入健康到数据库中");   
              	
              }else if(name.equals("推荐")){
                    recommendEntity = new RecommendEntity();
                    recommendEntity.set_blockID(data.getInt("blockID"));
                    recommendEntity.set_iconsEntity(iconsEntityArrayList); 
                    recommendEntity.set_name(data.getString("name")); 
                    recommendEntity.set_online(data.getBoolean("online")); 
                    recommendEntity.set_onlineMsg(data.getString("onlineMsg"));  
                    recommendEntity.set_sortID(data.getInt("sortID"));
                    RecommendDAO recommendDAO = new RecommendDAO(this.getApplicationContext());
                    recommendDAO.delete(null, null);
                    recommendDAO.insert(recommendEntity);  
                    Log.d(TAG,"正在插入推荐到数据库中");                                       
              	
              }else if(name.equals("教育")){
                    educationEntity = new EducationEntity();
                    educationEntity.set_blockID(data.getInt("blockID"));
                    educationEntity.set_iconsEntity(iconsEntityArrayList); 
                    educationEntity.set_name(data.getString("name")); 
                    educationEntity.set_online(data.getBoolean("online")); 
                    educationEntity.set_onlineMsg(data.getString("onlineMsg"));  
                    educationEntity.set_sortID(data.getInt("sortID"));
                    EducationDAO educationdao = new EducationDAO(this.getApplicationContext());
                    educationdao.delete(null, null);
                    educationdao.insert(educationEntity);                     
              	
              }else if(name.equals("影视")){
                    movieEntity = new MovieEntity();
                    movieEntity.set_blockID(data.getInt("blockID"));
                    movieEntity.set_iconsEntity(iconsEntityArrayList); 
                    movieEntity.set_name(data.getString("name")); 
                    movieEntity.set_online(data.getBoolean("online")); 
                    movieEntity.set_onlineMsg(data.getString("onlineMsg"));  
                    movieEntity.set_sortID(data.getInt("sortID"));
                    MovieDAO moviedao = new MovieDAO(this.getApplicationContext());
                    moviedao.delete(null, null);
                    moviedao.insert(movieEntity);                     
                     Log.d(TAG,"正在插入影视到数据库中");              	
              }else if(name.equals("应用")){
                    appEntity = new AppEntity();
                    appEntity.set_blockID(data.getInt("blockID"));
                    appEntity.set_iconsEntity(iconsEntityArrayList); 
                    appEntity.set_name(data.getString("name")); 
                    appEntity.set_online(data.getBoolean("online")); 
                    appEntity.set_onlineMsg(data.getString("onlineMsg"));  
                    appEntity.set_sortID(data.getInt("sortID"));
                    AppDAO appdao = new AppDAO(this.getApplicationContext());
                    appdao.delete(null, null);
                    appdao.insert(appEntity);                    
                     Log.d(TAG,"正在插入应用到数据库中");               	
              }else{

              	
              }           
                
            }
          //get Launcher info
          JSONObject  launcherinfo  = dataJson.getJSONObject("launcher");
		  launcherEntity = new LauncherEntity();  
		  launcherEntity.set_animVersion(launcherinfo.getString("animVersion"));
		  launcherEntity.set_animation(launcherinfo.getString("animation"));
		  launcherEntity.set_apk(launcherinfo.getString("apk"));
		  launcherEntity.set_description(launcherinfo.getString("description"));
		  launcherEntity.set_icon(launcherinfo.getString("icon"));
		  launcherEntity.set_launcherID(launcherinfo.getInt("launcherID"));
		  launcherEntity.set_online((launcherinfo.getBoolean("online"))?1:0);	
		  launcherEntity.set_onlineMsg(launcherinfo.getString("onlineMsg"));
		  launcherEntity.set_updateDate(launcherinfo.getString("updateDate"));
		  launcherEntity.set_version(launcherinfo.getString("version"));
	      LauncherDAO launcherDAO = new LauncherDAO(this.getApplicationContext());
	      launcherDAO.delete(null, null);
	      launcherDAO.insert(launcherEntity);  		  
		  	Log.d(TAG,"launcherinfo = "+launcherEntity.toString());
		  
		 
           //   Toast.makeText(Launcher.this, info, 10000).show(); 
      	
        	}catch(Exception e){
        	 Log.d(TAG,""+e);  
          }catch(Throwable v) {
           Log.d(TAG,""+v);   
        }
   		     	
    	
    } 
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {
    	   	 Log.d(TAG,"onStartCommand()");  
        return super.onStartCommand(intent, flags, startId);  
    }  
     @Override     
    protected void onHandleIntent(Intent intentArgs) {  
    	 Log.d(TAG,"onHandleIntent()");
    	 if(NetworkUtils.isNetAvailable(LoadService.this)){
    	     loadLauncherData();
    	     Intent i = new Intent();
           i.setAction(DATA_ACTION);
         this.sendBroadcast(i); 
         
         }    
    	
    	} 
 public void onDestroy() { 
 	 super.onDestroy();  
 	 Log.d(TAG,"onDestroy()"); 
 	
 	}	
 	
	
}
