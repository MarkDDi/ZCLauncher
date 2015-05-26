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
import com.word.wordinsidehome.utils.LogUtils;
import com.word.wordinsidehome.view.AppRecommendView;
import com.word.wordinsidehome.view.MyDialog;

import java.util.ArrayList;
import java.util.HashMap;

import com.word.wordinsidehome.service.entity.*;
import com.word.wordinsidehome.service.dao.*;
import com.word.wordinsidehome.service.image.*;
import com.word.wordinsidehome.AppStoreApplication;
import com.word.wordinsidehome.R;

public class MatrixAppView extends TabBasePageView {


    protected static final String TAG = "MatrixAppView";
    private TextView[] desViews;
    HashMap hashMap;
    private final Context mContext;
    private Handler mHandler;
    private TextView matrix_app_flipper_0_text_view;
    private TextView matrix_app_flipper_1_text_view;
    private TextView matrix_app_flipper_2_text_view;
    private TextView matrix_app_flipper_3_text_view;
    private TextView matrix_app_flipper_4_text_view;
    private TextView matrix_app_flipper_5_text_view;

    private View matrix_app_layout_0;
    private View matrix_app_layout_1;
    private View matrix_app_layout_2;
    private View matrix_app_layout_3;
    private View matrix_app_layout_4;
    private View matrix_app_layout_5;


    private ViewFlipper app_flipper_0;
    private ViewFlipper app_flipper_1;
    private ViewFlipper app_flipper_2;
    private ViewFlipper app_flipper_3;
    private ViewFlipper app_flipper_4;
    private ViewFlipper app_flipper_5;


    private View[] appLayouts;
    private AppDAO appDAO;


    public MatrixAppView(Context context) {
        super(context);
        this.mContext = context;
        this.init();
    }


    protected void init() {
        this.appDAO = new AppDAO(this.getContext());
        this.addView(MatrixAppView.inflate(this.getContext(), R.layout.matrix_app_view, null), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        this.matrix_app_layout_0 = (RelativeLayout) this.findViewById(R.id.matrix_app_layout_0);
        this.matrix_app_layout_1 = (RelativeLayout) this.findViewById(R.id.matrix_app_layout_1);
        this.matrix_app_layout_2 = (RelativeLayout) this.findViewById(R.id.matrix_app_layout_2);
        this.matrix_app_layout_3 = (RelativeLayout) this.findViewById(R.id.matrix_app_layout_3);
        this.matrix_app_layout_4 = (RelativeLayout) this.findViewById(R.id.matrix_app_layout_4);
        this.matrix_app_layout_5 = (RelativeLayout) this.findViewById(R.id.matrix_app_layout_5);

        this.app_flipper_0 = (ViewFlipper) this.findViewById(R.id.matrix_app_flipper_0);
        this.app_flipper_1 = (ViewFlipper) this.findViewById(R.id.matrix_app_flipper_1);
        this.app_flipper_2 = (ViewFlipper) this.findViewById(R.id.matrix_app_flipper_2);
        this.app_flipper_3 = (ViewFlipper) this.findViewById(R.id.matrix_app_flipper_3);
        this.app_flipper_4 = (ViewFlipper) this.findViewById(R.id.matrix_app_flipper_4);
        this.app_flipper_5 = (ViewFlipper) this.findViewById(R.id.matrix_app_flipper_5);

        this.matrix_app_flipper_0_text_view = (TextView) this.findViewById(R.id.matrix_app_flipper_0_text_view);
        this.matrix_app_flipper_1_text_view = (TextView) this.findViewById(R.id.matrix_app_flipper_1_text_view);
        this.matrix_app_flipper_2_text_view = (TextView) this.findViewById(R.id.matrix_app_flipper_2_text_view);
        this.matrix_app_flipper_3_text_view = (TextView) this.findViewById(R.id.matrix_app_flipper_3_text_view);
        this.matrix_app_flipper_4_text_view = (TextView) this.findViewById(R.id.matrix_app_flipper_4_text_view);
        this.matrix_app_flipper_5_text_view = (TextView) this.findViewById(R.id.matrix_app_flipper_5_text_view);
        this.flippers = new ViewFlipper[]{this.app_flipper_0, this.app_flipper_1, this.app_flipper_2, this.app_flipper_3, this.app_flipper_4, this.app_flipper_5};
        this.appLayouts = new View[]{this.matrix_app_layout_0, this.matrix_app_layout_1, this.matrix_app_layout_2, this.matrix_app_layout_3, this.matrix_app_layout_4, this.matrix_app_layout_5};

        for (int v0 = 0; v0 < this.appLayouts.length; ++v0) {
            this.appLayouts[v0].setOnClickListener(new appLayoutClickListener(this));
        }

        this.matrix_app_layout_0.setNextFocusUpId(R.id.navigation_tab_app_text);
        this.matrix_app_layout_0.setNextFocusRightId(R.id.matrix_app_layout_1);
        this.matrix_app_layout_0.setNextFocusLeftId(R.id.matrix_movie_layout_3);
        this.matrix_app_layout_0.setNextFocusDownId(R.id.matrix_app_layout_3);

        this.matrix_app_layout_1.setNextFocusUpId(R.id.navigation_tab_app_text);
        this.matrix_app_layout_1.setNextFocusRightId(R.id.matrix_app_layout_2);
        this.matrix_app_layout_1.setNextFocusLeftId(R.id.matrix_app_layout_0);
        this.matrix_app_layout_1.setNextFocusDownId(R.id.matrix_app_layout_4);

        this.matrix_app_layout_2.setNextFocusUpId(R.id.navigation_tab_app_text);
        this.matrix_app_layout_2.setNextFocusRightId(R.id.matrix_health_layout_0);
        this.matrix_app_layout_2.setNextFocusLeftId(R.id.matrix_app_layout_1);
        this.matrix_app_layout_2.setNextFocusDownId(R.id.sub_navigation_home_external_text);

        this.matrix_app_layout_3.setNextFocusUpId(R.id.matrix_app_layout_0);
        this.matrix_app_layout_3.setNextFocusRightId(R.id.matrix_app_layout_4);
        this.matrix_app_layout_3.setNextFocusLeftId(R.id.matrix_movie_layout_6);
        this.matrix_app_layout_3.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

        this.matrix_app_layout_4.setNextFocusUpId(R.id.matrix_app_layout_1);
        this.matrix_app_layout_4.setNextFocusRightId(R.id.matrix_app_layout_5);
        this.matrix_app_layout_4.setNextFocusLeftId(R.id.matrix_app_layout_3);
        this.matrix_app_layout_4.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

        this.matrix_app_layout_5.setNextFocusUpId(R.id.matrix_app_layout_1);
        this.matrix_app_layout_5.setNextFocusRightId(R.id.matrix_app_layout_2);
        this.matrix_app_layout_5.setNextFocusLeftId(R.id.matrix_app_layout_4);
        this.matrix_app_layout_5.setNextFocusDownId(R.id.sub_navigation_home_external_text);

        super.init();
    }

    public void loadData(boolean isRefleshData) {
        LogUtils.d("loadData() called");
        setMatrixData(isRefleshData);
    }

    class appLayoutClickListener implements View.OnClickListener {
        appLayoutClickListener(MatrixAppView arg1) {
            super();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "===== onClick()");
            doOnClick(v);
    /*	int id = v.getId();
        Log.d(TAG,"===== onClick(), id = " + id);


		 if (id == R.id.matrix_app_layout_0) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.word.wordinsideapp",
					"com.word.wordinsideapp.MainAppStore"));

			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
		  }else if (id == R.id.matrix_app_layout_1) {
		  	
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.tencent.QQVideo",
					"com.tencent.QQVideo.Login.SplashScreen"));

			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
	   }else if (id == R.id.matrix_app_layout_2) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("st.com.xiami",
					"com.yunos.xiami.activity.MainActivity"));

			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 

	   }else if (id == R.id.matrix_app_layout_3) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.qiyi.video",
					"com.qiyi.video.ui.home.HomeActivity"));

			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
	   }else if (id == R.id.matrix_app_layout_4) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.xiaobaifile.tv",
					"com.xiaobaifile.tv.view.StartupActivity"));

			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);  
	   }else if (id == R.id.matrix_app_layout_5) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("com.x_office_r_tv",
					"com.hyfsoft.RecentFileActivity"));

			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);       
	   }*/
        }
    }

    ArrayList appList;

    public void setMatrixData(boolean isRefleshData) {
        MatrixAppView.this.appList = MatrixAppView.this.appDAO.queryMatrixApp(null, null, null, null);
        int apps = this.appList.size();
        LogUtils.d("apps called size =" + apps);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

        for (int temp = 0; temp < apps; ++temp) {

            // appList中每个元素存放的是ArrayList<IconsEntity>
            Object iconsEntity = this.appList.get(temp);
            int icons = ((ArrayList) iconsEntity).size();
            // flipper为每个应用卡片，ViewFlipper
            int flipperChildCount = this.flippers[temp].getChildCount();
            int i;
            if (icons > flipperChildCount) {

                for (i = 0; i < icons - flipperChildCount; ++i) {
                    this.flippers[temp].addView(new AppRecommendView(this.getContext()), params);
                }
            } else if (icons < flipperChildCount) {
                for (i = icons; i < flipperChildCount; ++i) {
                    this.flippers[temp].removeViewAt(i);
                }
            }

            if (((ArrayList) iconsEntity).size() > 0) {
                // 设置ViewFlipper切换的时间间隔
                this.flippers[temp].setFlipInterval(5000);
            }


            for (int k = 0; k < icons; ++k) {
                // entitys为IconsEntity
                Object entitys = ((ArrayList) iconsEntity).get(k);
                Object v6 = null;
                View v8 = this.flippers[k].getChildAt(k);
                if (((AppRecommendView) v8).getTag() != null) {
                    v6 = ((AppRecommendView) v8).getTag();
                }
                Log.d("zzktag", "setMatrixData() v6 =" + v6);
                if (v6 == null || ((IconsEntity) v6).get_appID() != ((IconsEntity) entitys).get_appID() || !((IconsEntity) v6).get_icon().equals(((IconsEntity) entitys).get_icon())) {
                    ((AppRecommendView) v8).setTag(entitys);
                    this.mapDownload.put(((IconsEntity) entitys).get_appName(), v8);


                }
                ImageLoader.getInstance().displayImage(((IconsEntity) entitys).get_icon(), ((AppRecommendView) v8).getImageView(), isRefleshData);
            }
        }

    }

    public void doOnClick(View view) {
        int indet = -1;
        int tmp = 0;
        while (tmp < MatrixAppView.this.appLayouts.length) {
            if (view == MatrixAppView.this.appLayouts[tmp]) {
                indet = tmp;
            } else {
                ++tmp;
                continue;
            }
            break;
        }

        if (indet < 0 || indet >= MatrixAppView.this.flippers.length) {

        } else if (MatrixAppView.this.flippers[indet].getCurrentView() != null && MatrixAppView.this.flippers[indet].getChildCount() != 0) {
            Object obj = MatrixAppView.this.flippers[indet].getCurrentView().getTag();
            if (obj != null) {

                try {
                    String packageName = ((IconsEntity) obj).get_packageName();
                    String className = ((IconsEntity) obj).get_className();
                    Intent startIntent = new Intent();
                    startIntent.setComponent(new ComponentName(packageName, className));
                    startIntent.setAction("android.intent.action.MAIN");
                    mContext.startActivity(startIntent);
                } catch (Exception v0) {
                    showAppDialog((IconsEntity) obj);
                    Log.d(TAG, "error= " + v0);
                    return;
                }


            }
        }

    }

    private void showAppDialog(IconsEntity iconInfo) {
        final IconsEntity miconInfo = iconInfo;
        new MyDialog(mContext, iconInfo, "应用") {
            public void onSetMessage(View title, View body) {
                // ((TextView)title).setText(R.string.extern_db_confirm);
                // ((TextView)body).setText(db_path);
            }

            public void onSetNegativeButton() {


            }

            public void onSetPositiveButton() {

                AppStoreApplication.beginNewDownloadThread(miconInfo);
                //  AppDownloadThread appLoadThread = new AppDownloadThread(mContext,miconInfo);
                //  appLoadThread.download(miconInfo);
                //Log.d(TAG,"dialog choise=");

            }
        };
    }

    @Override
    public void onAppStoreReceive(Intent intent) {
        Object v3;
        IconsEntity appEntityReceiver = (IconsEntity) intent.getSerializableExtra("AppEntity");
        String action = intent.getAction();
        Log.d("zzkd", "action= " + action);
        v3 = this.mapDownload.get(appEntityReceiver.get_appName());
        Log.d("zzkd", "v3= " + v3);
        if (v3 == null) {
            return;
        }

        if ("com.hiveview.appstore.home.download_start".equals(action)) {

            ((AppRecommendView) v3).startDownloadAnim();
            return;
        }


        if ("com.hiveview.appstore.home.download_complete".equals(action) || "com.hiveview.appstore.download_failed".equals(action)) {

            ((AppRecommendView) v3).stopDownloadAnim();
            return;
        }


        ((AppRecommendView) v3).stopDownloadAnim();

    }

    public void updateUIRefleshData() {
    }
}

