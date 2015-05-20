package com.word.wordinsidehome.view;

import java.util.*;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;


import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.text.method.*;
import android.graphics.Color;
import com.word.wordinsidehome.service.entity.*;
import com.word.wordinsidehome.image.*;
import com.word.wordinsidehome.R;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

abstract public class MyDialog {
	
	    class AppBroadcastReceiver extends BroadcastReceiver {
	    	    protected IconsEntity appEntityReceiver;
	    	  	protected int progress;    

			@Override
			public void onReceive(Context context, Intent intent) {
				appEntityReceiver = (IconsEntity)intent.getSerializableExtra("AppEntity");
				String action = intent.getAction();
			if(appEntityReceiver.get_apk().equals(myiconInfo.get_apk())){	
				 Log.d(TAG,"download appName ="+appEntityReceiver.get_appName());
            if("com.hiveview.appstore.download_progress".equals(action)) {

             progress = intent.getIntExtra("download_progress", 50);
             freeLoad.setVisibility(View.GONE);
             app_down_progBar.setVisibility(View.VISIBLE);
             app_down_progBar.setProgress(progress);
           Log.d(TAG,"receive download_progress ="+progress);
            }else if("com.hiveview.appstore.home_install_success".equals(action)) {
            Toast toast = Toast.makeText(context,
            R.string.app_install_success,
            Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show(); 
            	
             mDialog.dismiss();
           Log.d(TAG,"receive install_success ");


        }else if("com.hiveview.appstore.home_install_begin".equals(action)) {
        	   description.setVisibility(View.GONE);
        	   app_detail_install.setVisibility(View.VISIBLE);
             app_detail_install.setText(String.format(app_detail_installStr,"应用正在安装，请稍后！"));            
           Log.d(TAG,"receive home_install_begin ");


        }else if("com.hiveview.appstore.home_install_fail".equals(action)) {
        	   	description.setVisibility(View.GONE);
        	    app_detail_install.setVisibility(View.VISIBLE);
             app_detail_install.setText(String.format(app_detail_installStr,"应用安装失败，请从试！"));            
           Log.d(TAG,"receive home_install_FAIL ");


        }else if("com.hiveview.appstore.download_failed".equals(action)) {
        	
                     freeLoad.setVisibility(View.VISIBLE);
                     app_down_progBar.setVisibility(View.GONE);          	
        	           description.setVisibility(View.VISIBLE);
        	           app_detail_install.setVisibility(View.GONE);          
           Log.d(TAG,"receive download_failed ");

        }
       }else{
        if("com.hiveview.appstore.download_busy".equals(action)) {
          Toast toast = Toast.makeText(context,
            R.string.download_busy,
            Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show(); 
        	
             Log.d(TAG,"receive download_busy");


        }
      }
    }
  
 }
	
	
	private static final String TAG="MyDialog";
	public Dialog mDialog = null;
	private Context mContext = null;
  private String appNameStr;
  private String appTypeStr;
  private String appIDStr;
  private String paramsStr;
  private String versionStr;
  private String descriptionStr;
   private String app_detail_installStr;
  String typeHolder;
  
	TextView appName;
	TextView appType;
	TextView appID;
	TextView params;
	TextView version;
  TextView	description;
  TextView  app_detail_install;
	ImageView freeLoad;
	ImageView app_detail_icon;	
	IconsEntity myiconInfo;
	ProgressBar app_down_progBar;
	private AppBroadcastReceiver appBroadcastReceiver;
	public MyDialog(Context context,IconsEntity iconInfo ,String type) {
		    
		mContext = context;
		myiconInfo = iconInfo;
		typeHolder = type;
		mDialog = new AlertDialog(mContext){
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event){
				 switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:	
						dismissDialog();
						return true;
					case KeyEvent.KEYCODE_DPAD_LEFT:
					//	if(no!=null)
					//		no.requestFocus();
						break;
					case KeyEvent.KEYCODE_DPAD_RIGHT:
					//	if(yes!=null)
					//		yes.requestFocus();
						break;
						
				}
				return super.onKeyDown(keyCode, event);
			}
			
		};
		
		mDialog.setCancelable(false);
		mDialog.setCanceledOnTouchOutside(false);

		if(mDialog == null){
			return;
		}

		mDialog.setOnShowListener(new DialogInterface.OnShowListener(){
			public void onShow(DialogInterface dialog) {
				onShowEvent();
			}         
		}); 	

		mDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
						public void onDismiss(DialogInterface dialog) {
							onDismissEvent();
						}         
						});	
		
		mDialog.show();
		Window window = mDialog.getWindow();
		mDialog.setContentView(R.layout.app_store_detail_layout);
		WindowManager.LayoutParams lp=mDialog.getWindow().getAttributes();
		lp.dimAmount=0.25f;
		mDialog.getWindow().setAttributes(lp);
		mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialogInit(window);
		
	  IntentFilter intentFilter = new IntentFilter();
	  intentFilter.addAction("com.hiveview.appstore.download_progress");
	  intentFilter.addAction("com.hiveview.appstore.home_install_begin");
	  intentFilter.addAction("com.hiveview.appstore.home_install_fail");
	  intentFilter.addAction("com.hiveview.appstore.home_install_success");	  	
	  intentFilter.addAction("com.hiveview.appstore.download_failed");	
	  intentFilter.addAction("com.hiveview.appstore.download_busy");	
	  
	  this.appBroadcastReceiver = new AppBroadcastReceiver();
	  context.registerReceiver(this.appBroadcastReceiver, intentFilter);		
	}

	public void onShowEvent(){}
	public void onDismissEvent(){}
	
	private void dialogInit(Window window){
		appNameStr = mContext.getResources().getString(R.string.app_detail_name);
		appTypeStr = mContext.getResources().getString(R.string.app_detail_type);
		appIDStr = mContext.getResources().getString(R.string.app_detail_appid);
		appTypeStr = mContext.getResources().getString(R.string.app_detail_type);
		paramsStr = mContext.getResources().getString(R.string.app_detail_params);								
		versionStr = mContext.getResources().getString(R.string.app_detail_version);
		descriptionStr = mContext.getResources().getString(R.string.app_detail_intro_info);		
		app_detail_installStr  = mContext.getResources().getString(R.string.app_detail_install);	
					
    appName  = (TextView)window.findViewById(R.id.app_detail_name);
    appName.setText(String.format(appNameStr,myiconInfo.get_appName()));
    appType  = (TextView)window.findViewById(R.id.app_detail_type);
    appType.setText(String.format(appTypeStr,typeHolder)); 
    appID  = (TextView)window.findViewById(R.id.app_ID);  
    appID.setText(String.format(appIDStr,myiconInfo.get_appID()));         
    params  = (TextView)window.findViewById(R.id.app_detail_params);  
    params.setText(String.format(paramsStr,myiconInfo.get_params()));            
    version  = (TextView)window.findViewById(R.id.app_detail_version);  
    version.setText(String.format(versionStr,myiconInfo.get_version()));     
    description  = (TextView)window.findViewById(R.id.app_detail_intro_info); 
    description.setText(String.format(descriptionStr,myiconInfo.get_description()));  
	  freeLoad = (ImageView)window.findViewById(R.id.app_detail_button_free);
	  app_detail_icon = (ImageView)window.findViewById(R.id.app_detail_icon);
    app_detail_install	= (TextView)window.findViewById(R.id.app_detail_install);    
	  	  
     ImageLoader.getInstance().displayImage(myiconInfo.get_icon(),app_detail_icon,true);
       Log.d(TAG,"myiconInfo.get_icon()= "+myiconInfo.get_icon());
     
   	  app_down_progBar = (ProgressBar)window.findViewById(R.id.app_detail_download_statue);  
	  	onSetMessage(appName,appType);
 
		freeLoad.setOnClickListener(new OnClickListener(){
		          public void onClick(View v) {
					 onSetPositiveButton();
				//	 dismissDialog();
		          }});
				  
	}
		
	
	public void dismissDialog(){
		if(mDialog!=null&& mDialog.isShowing()){
			mDialog.dismiss();
		}
	}

	public boolean isShowing(){
		if(mDialog!=null&&mDialog.isShowing()){
			return mDialog.isShowing();
		}
		return false;
	}

	
	abstract public void onSetMessage(View t,View b);
	abstract public void onSetNegativeButton();
	abstract public void onSetPositiveButton();
}

 
