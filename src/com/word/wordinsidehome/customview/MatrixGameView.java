package com.word.wordinsidehome.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.view.ViewGroup.*;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.Toast;
import android.view.Gravity; 
import android.util.Log;
import com.word.wordinsidehome.customview.pager3d.TabBasePageView;
import com.word.wordinsidehome.customview.pager3d.TabBasePageView.ViewFocusDirectionListener;
import com.word.wordinsidehome.view.AppRecommendView;
import com.word.wordinsidehome.view.MyDialog;
import java.util.ArrayList;
import java.util.HashMap;
import com.word.wordinsidehome.service.entity.*;
import com.word.wordinsidehome.service.dao.*;
import com.word.wordinsidehome.service.image.*;
import com.word.wordinsidehome.AppStoreApplication;
import com.word.wordinsidehome.R;

public class MatrixGameView extends TabBasePageView {




    protected static final String TAG = "MatrixGameView";
    private TextView[] desViews;
    HashMap hashMap;
    private final Context mContext;
    private Handler mHandler;
    private TextView matrix_game_flipper_0_text_view;
    private TextView matrix_game_flipper_1_text_view;
    private TextView matrix_game_flipper_2_text_view;
    private TextView matrix_game_flipper_3_text_view;
    private TextView matrix_game_flipper_4_text_view;
    private TextView matrix_game_flipper_5_text_view;
    private TextView matrix_game_flipper_6_text_view;    
    private View matrix_game_layout_0;
    private View matrix_game_layout_1;
    private View matrix_game_layout_2;
    private View matrix_game_layout_3;
    private View matrix_game_layout_4;
    private View matrix_game_layout_5;
    private View matrix_game_layout_6;    

    private ViewFlipper game_flipper_0;
    private ViewFlipper game_flipper_1;
    private ViewFlipper game_flipper_2;
    private ViewFlipper game_flipper_3;
    private ViewFlipper game_flipper_4;
    private ViewFlipper game_flipper_5;
    private ViewFlipper game_flipper_6; 


    private View[] gameLayouts;
    private GameDAO gameDAO;


    public MatrixGameView(Context context) {
        super(context);
        this.mContext=context;
        this.init();
    }



    protected void init() {
        this.gameDAO = new GameDAO(this.getContext());
        this.addView(MatrixGameView.inflate(this.getContext(), R.layout.matrix_game_view, null), new RelativeLayout.LayoutParams(
                 RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT));
                 
        this.matrix_game_layout_0 = (RelativeLayout)this.findViewById(R.id.matrix_game_layout_0);
        this.matrix_game_layout_1 = (RelativeLayout)this.findViewById(R.id.matrix_game_layout_1);
        this.matrix_game_layout_2 = (RelativeLayout)this.findViewById(R.id.matrix_game_layout_2);
        this.matrix_game_layout_3 = (RelativeLayout)this.findViewById(R.id.matrix_game_layout_3);
        this.matrix_game_layout_4 = (RelativeLayout)this.findViewById(R.id.matrix_game_layout_4);
        this.matrix_game_layout_5 = (RelativeLayout)this.findViewById(R.id.matrix_game_layout_5);
        this.matrix_game_layout_6 = (RelativeLayout)this.findViewById(R.id.matrix_game_layout_6);       
        this.game_flipper_0 = (ViewFlipper)this.findViewById(R.id.matrix_game_flipper_0);
        this.game_flipper_1 = (ViewFlipper)this.findViewById(R.id.matrix_game_flipper_1);
        this.game_flipper_2 = (ViewFlipper)this.findViewById(R.id.matrix_game_flipper_2);
        this.game_flipper_3 = (ViewFlipper)this.findViewById(R.id.matrix_game_flipper_3);
        this.game_flipper_4 = (ViewFlipper)this.findViewById(R.id.matrix_game_flipper_4);
        this.game_flipper_5 = (ViewFlipper)this.findViewById(R.id.matrix_game_flipper_5);
        this.game_flipper_6 = (ViewFlipper)this.findViewById(R.id.matrix_game_flipper_6);        
        this.matrix_game_flipper_0_text_view = (TextView)this.findViewById(R.id.matrix_game_flipper_0_text_view);
        this.matrix_game_flipper_1_text_view = (TextView)this.findViewById(R.id.matrix_game_flipper_1_text_view);
        this.matrix_game_flipper_2_text_view = (TextView)this.findViewById(R.id.matrix_game_flipper_2_text_view);
        this.matrix_game_flipper_3_text_view = (TextView)this.findViewById(R.id.matrix_game_flipper_3_text_view);
        this.matrix_game_flipper_4_text_view = (TextView)this.findViewById(R.id.matrix_game_flipper_4_text_view);
        this.matrix_game_flipper_5_text_view = (TextView)this.findViewById(R.id.matrix_game_flipper_5_text_view);
        this.matrix_game_flipper_6_text_view = (TextView)this.findViewById(R.id.matrix_game_flipper_6_text_view); 
        this.flippers = new ViewFlipper[]{this.game_flipper_0, this.game_flipper_1, this
                .game_flipper_2, this.game_flipper_3, game_flipper_4, game_flipper_5, game_flipper_6};        
         
        this.gameLayouts = new View[]{this.matrix_game_layout_0, this.matrix_game_layout_1, this.matrix_game_layout_2, 
                this.matrix_game_layout_3, this.matrix_game_layout_4, this.matrix_game_layout_5, this
                .matrix_game_layout_6};

        for(int v0 = 0; v0 < this.gameLayouts.length; ++v0) {
            this.gameLayouts[v0].setOnClickListener(new gameLayoutClickListener(this));
        }
              
        this.matrix_game_layout_0.setNextFocusUpId(R.id.navigation_tab_game_text);
        this.matrix_game_layout_0.setNextFocusRightId(R.id.matrix_game_layout_1);
        this.matrix_game_layout_0.setNextFocusLeftId(R.id.matrix_health_layout_3);
        this.matrix_game_layout_0.setNextFocusDownId(R.id.sub_navigation_home_setting_text);
        
        this.matrix_game_layout_1.setNextFocusUpId(R.id.navigation_tab_game_text);
        this.matrix_game_layout_1.setNextFocusRightId(R.id.matrix_game_layout_2);
        this.matrix_game_layout_1.setNextFocusLeftId(R.id.matrix_game_layout_0);
        this.matrix_game_layout_1.setNextFocusDownId(R.id.matrix_game_layout_4);
        
        this.matrix_game_layout_2.setNextFocusUpId(R.id.navigation_tab_game_text);
        this.matrix_game_layout_2.setNextFocusRightId(R.id.matrix_game_layout_3);
        this.matrix_game_layout_2.setNextFocusLeftId(R.id.matrix_game_layout_1);
        this.matrix_game_layout_2.setNextFocusDownId(R.id.matrix_game_layout_4);
        
        this.matrix_game_layout_3.setNextFocusUpId(R.id.navigation_tab_game_text);
        this.matrix_game_layout_3.setNextFocusRightId(R.id.matrix_recommend_layout_0);
        this.matrix_game_layout_3.setNextFocusLeftId(R.id.matrix_game_layout_2);
        this.matrix_game_layout_3.setNextFocusDownId(R.id.matrix_game_layout_5);
        
        this.matrix_game_layout_4.setNextFocusUpId(R.id.matrix_game_layout_1);
        this.matrix_game_layout_4.setNextFocusRightId(R.id.matrix_game_layout_5);
        this.matrix_game_layout_4.setNextFocusLeftId(R.id.matrix_game_layout_0);
        this.matrix_game_layout_4.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);
        
        this.matrix_game_layout_5.setNextFocusUpId(R.id.matrix_game_layout_3);
        this.matrix_game_layout_5.setNextFocusRightId(R.id.matrix_recommend_layout_0);
        this.matrix_game_layout_5.setNextFocusLeftId(R.id.matrix_game_layout_2);
        this.matrix_game_layout_5.setNextFocusDownId(R.id.matrix_game_layout_6); 
            
        this.matrix_game_layout_6.setNextFocusUpId(R.id.matrix_game_layout_5);
        this.matrix_game_layout_6.setNextFocusRightId(R.id.matrix_recommend_layout_3);
        this.matrix_game_layout_6.setNextFocusLeftId(R.id.matrix_game_layout_4);
        this.matrix_game_layout_6.setNextFocusDownId(R.id.sub_navigation_home_external_text);     
        super.init();
    }
    public void loadData(boolean isRefleshData) {
    	        Log.d("zzktag","loadData() called");
             setMatrixData(isRefleshData);
    }    
    
    
    
class gameLayoutClickListener implements View.OnClickListener {
	        gameLayoutClickListener(MatrixGameView arg1) {
            super();
	        	}
@Override
public void onClick(View v) {
	   Log.d(TAG,"===== onClick()");
	  doOnClick(v);
	/*	int id = v.getId();
        Log.d(TAG,"===== onClick(), id = " + id);


		 if (id == R.id.matrix_game_layout_0) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.TDiJoy.Tennis",
					"com.TDiJoy.Tennis.LoadActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
		  }else if (id == R.id.matrix_game_layout_1) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.TDiJoy.PingpongdarenOffline",
					"com.TDiJoy.PingpongdarenOffline.LoadActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
	   }else if (id == R.id.matrix_game_layout_2) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.TDiJoy.Bowling",
					"com.TDiJoy.Bowling.LoadActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
	   }else if (id == R.id.matrix_game_layout_3) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.TDiJoy.RobotUnicorn",
					"com.TDiJoy.RobotUnicorn.LoadActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);  
	   }else if (id == R.id.matrix_game_layout_4) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.winside.game",
					"com.winside.MainActivity_mrdriller"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);  
	   }else if (id == R.id.matrix_game_layout_5) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.TDiJoy.RichmanOffline",
					"com.TDiJoy.RichmanOffline.LoadActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);        
	   }else if(id == R.id.matrix_game_layout_6) {

   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.TDiJoy.fane",
					"com.TDiJoy.fane.activity.LoadActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);          
	   }*/
  }
}
        public void doOnClick(View view) {
            int indet = -1;
            int tmp = 0;
            while(tmp < MatrixGameView.this.gameLayouts.length) {
                if(view == MatrixGameView.this.gameLayouts[tmp]) {
                    indet = tmp;
                }
                else {
                    ++tmp;
                    continue;
                }
                break;
            }

            if(indet < 0 || indet >= MatrixGameView.this.flippers.length) {
              
              }
            else if(MatrixGameView.this.flippers[indet].getCurrentView() != null && MatrixGameView.this
                    .flippers[indet].getChildCount() != 0) {
                Object obj = MatrixGameView.this.flippers[indet].getCurrentView().getTag();
                if(obj != null) {
                    try { 
                       String packageName = ((IconsEntity)obj).get_packageName();
                       String className = ((IconsEntity)obj).get_className();                       
												Intent startIntent = new Intent();
												startIntent.setComponent(new ComponentName(packageName,className));	
												startIntent.setAction("android.intent.action.MAIN");
												mContext.startActivity(startIntent);
										}catch(Exception v0) {
											   showAppDialog((IconsEntity)obj);
              	        Log.d(TAG,"error= " +v0);
              	        return;
                    } 

                }
            }

        }

 private void  showAppDialog(IconsEntity iconInfo) { 
 	 final  IconsEntity miconInfo   = iconInfo; 
 	new MyDialog(mContext,iconInfo,"游戏"){ 
 		public void onSetMessage(View title,View body){ 
 	// ((TextView)title).setText(R.string.extern_db_confirm);
 	// ((TextView)body).setText(db_path); 
 	}

          public void onSetNegativeButton(){
         	

            }
            public void onSetPositiveButton(){
            	     AppStoreApplication.beginNewDownloadThread(miconInfo);
          	     //  AppDownloadThread appLoadThread = new AppDownloadThread(mContext,miconInfo);
         	       //  appLoadThread.download(miconInfo); 
                    Log.d(TAG,"dialog choise=");

            }
    };
    
   }
ArrayList gameList;

public void setMatrixData(boolean isRefleshData) {
    MatrixGameView.this.gameList = MatrixGameView.this.gameDAO.queryMatrixGame(null, null, null, null);	
    int v0 = this.gameList.size();
        Log.d("zzktag","setMatrixData() called size ="+v0);
    RelativeLayout.LayoutParams v7 =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT ,RelativeLayout.LayoutParams.FILL_PARENT);
    int v2;
    for(v2 = 0; v2 < v0; ++v2) {
        Object v9 = this.gameList.get(v2);
        int v1 = ((ArrayList)v9).size();
        int v10 = this.flippers[v2].getChildCount();
        int v3;
        if(v1 > v10) {

            for(v3 = 0; v3 < v1 - v10; ++v3) {
                this.flippers[v2].addView(new AppRecommendView(this.getContext()), v7);
            }
        }
        else if(v1 < v10) {
            for(v3 = v1; v3 < v10; ++v3) {
                this.flippers[v2].removeViewAt(v3);
            }
        }

        if(((ArrayList)v9).size() > 0) {
            this.flippers[v2].setFlipInterval(5000);
        }

        int v4;
        for(v4 = 0; v4 < v1; ++v4) {
            Object v5 = ((ArrayList)v9).get(v4);
            Object v6 = null;
            View v8 = this.flippers[v2].getChildAt(v4);
            if(((AppRecommendView)v8).getTag() != null) {
                v6 = ((AppRecommendView)v8).getTag();
            }
           Log.d("zzktag","setMatrixData() v6 ="+v6);
            if(v6 == null || ((IconsEntity)v6).get_appID() != ((IconsEntity)v5).get_appID()
                     || !((IconsEntity)v6).get_icon().equals(((IconsEntity)v5).get_icon())
                    ) {
                ((AppRecommendView)v8).setTag(v5);
				  this.mapDownload.put(((IconsEntity)v5).get_appName(),v8);


            }
                 ImageLoader.getInstance().displayImage(((IconsEntity)v5).get_icon(), ((
                        AppRecommendView)v8).getImageView(),isRefleshData);			
        }
    }

}
    public void onAppStoreReceive(Intent intent) {
        Object v3;		
		IconsEntity appEntityReceiver = (IconsEntity)intent.getSerializableExtra("AppEntity");
		String action = intent.getAction();	
		Log.d("zzkd","action= " +action);
        v3 = this.mapDownload.get(appEntityReceiver.get_appName());
			Log.d("zzkd","v3= " +v3);
        if(v3 == null) {
            return;
        }
		
        if("com.hiveview.appstore.home.download_start".equals(action)) {

            ((AppRecommendView)v3).startDownloadAnim();
            return;
        }

		
        if("com.hiveview.appstore.home.download_complete".equals(action)||"com.hiveview.appstore.download_failed".equals(action)) {

            ((AppRecommendView)v3).stopDownloadAnim();
            return;
        }
		


         ((AppRecommendView)v3).stopDownloadAnim();
            
    } 




    public void updateUIRefleshData() {
    }
}

