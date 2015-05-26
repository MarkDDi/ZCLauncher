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

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

import java.util.Iterator;

import android.view.MotionEvent;

import com.word.wordinsidehome.R;

public class MatrixMovieView extends TabBasePageView {


    protected static final String TAG = "MatrixMovieView";
    private TextView[] desViews;
    HashMap hashMap;
    private final Context mContext;
    @SuppressLint(value = {"HandlerLeak"})
    private Handler mHandler;
    private TextView matrix_movie_flipper_0_text_view;
    private TextView matrix_movie_flipper_1_text_view;
    private TextView matrix_movie_flipper_2_text_view;
    private TextView matrix_movie_flipper_3_text_view;
    private TextView matrix_movie_flipper_4_text_view;
    private TextView matrix_movie_flipper_5_text_view;
    private TextView matrix_movie_flipper_6_text_view;
    private TextView matrix_movie_flipper_7_text_view;

    private View matrix_movie_layout_0;
    private View matrix_movie_layout_1;
    private View matrix_movie_layout_2;
    private View matrix_movie_layout_3;
    private View matrix_movie_layout_4;
    private View matrix_movie_layout_5;
    private View matrix_movie_layout_6;
    private View matrix_movie_layout_7;

    private ViewFlipper movie_flipper_0;
    private ViewFlipper movie_flipper_1;
    private ViewFlipper movie_flipper_2;
    private ViewFlipper movie_flipper_3;
    private ViewFlipper movie_flipper_4;
    private ViewFlipper movie_flipper_5;
    private ViewFlipper movie_flipper_6;
    private ViewFlipper movie_flipper_7;


    private View[] movieLayouts;
    private MovieDAO movieDAO;


    public MatrixMovieView(Context context) {
        super(context);
        this.mContext = context;
        this.init();
    }


    protected void init() {
        this.movieDAO = new MovieDAO(this.getContext());
        this.addView(MatrixMovieView.inflate(this.getContext(), R.layout.matrix_movie_view, null), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        this.matrix_movie_layout_0 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_0);
        this.matrix_movie_layout_1 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_1);
        this.matrix_movie_layout_2 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_2);
        this.matrix_movie_layout_3 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_3);
        this.matrix_movie_layout_4 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_4);
        this.matrix_movie_layout_5 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_5);
        this.matrix_movie_layout_6 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_6);
        this.matrix_movie_layout_7 = (RelativeLayout) this.findViewById(R.id.matrix_movie_layout_7);

        this.movie_flipper_0 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_0);
        this.movie_flipper_1 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_1);
        this.movie_flipper_2 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_2);
        this.movie_flipper_3 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_3);
        this.movie_flipper_4 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_4);
        this.movie_flipper_5 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_5);
        this.movie_flipper_6 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_6);
        this.movie_flipper_7 = (ViewFlipper) this.findViewById(R.id.matrix_movie_flipper_7);

        this.matrix_movie_flipper_0_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_0_text_view);
        this.matrix_movie_flipper_1_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_1_text_view);
        this.matrix_movie_flipper_2_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_2_text_view);
        this.matrix_movie_flipper_3_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_3_text_view);
        this.matrix_movie_flipper_4_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_4_text_view);
        this.matrix_movie_flipper_5_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_5_text_view);
        this.matrix_movie_flipper_6_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_6_text_view);
        this.matrix_movie_flipper_7_text_view = (TextView) this.findViewById(R.id.matrix_movie_flipper_7_text_view);
        this.flippers = new ViewFlipper[]{this.movie_flipper_0, this.movie_flipper_1, this.movie_flipper_2, this.movie_flipper_3, this.movie_flipper_4, this.movie_flipper_5, this.movie_flipper_6, this.movie_flipper_7};
        for (int v0 = 0; v0 < this.flippers.length; ++v0) {

        }
        this.movieLayouts = new View[]{this.matrix_movie_layout_0, this.matrix_movie_layout_1, this.matrix_movie_layout_2, this.matrix_movie_layout_3, this.matrix_movie_layout_4, this.matrix_movie_layout_5, this.matrix_movie_layout_6, this.matrix_movie_layout_7};

        for (int v0 = 0; v0 < this.movieLayouts.length; ++v0) {
            this.movieLayouts[v0].setOnClickListener(new movieLayoutClickListener(this));
            this.movieLayouts[v0].setOnTouchListener(new MyOnTouchListener(this));
        }

        this.matrix_movie_layout_0.setNextFocusUpId(R.id.navigation_tab_movie_text);
        this.matrix_movie_layout_0.setNextFocusRightId(R.id.matrix_movie_layout_1);
        this.matrix_movie_layout_0.setNextFocusLeftId(R.id.matrix_education_layout_6);
        this.matrix_movie_layout_0.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

        this.matrix_movie_layout_1.setNextFocusUpId(R.id.navigation_tab_movie_text);
        this.matrix_movie_layout_1.setNextFocusRightId(R.id.matrix_movie_layout_2);
        this.matrix_movie_layout_1.setNextFocusLeftId(R.id.matrix_movie_layout_0);
        this.matrix_movie_layout_1.setNextFocusDownId(R.id.matrix_movie_layout_4);

        this.matrix_movie_layout_2.setNextFocusUpId(R.id.navigation_tab_movie_text);
        this.matrix_movie_layout_2.setNextFocusRightId(R.id.matrix_movie_layout_3);
        this.matrix_movie_layout_2.setNextFocusLeftId(R.id.matrix_movie_layout_1);
        this.matrix_movie_layout_2.setNextFocusDownId(R.id.matrix_movie_layout_5);

        this.matrix_movie_layout_3.setNextFocusUpId(R.id.navigation_tab_movie_text);
        this.matrix_movie_layout_3.setNextFocusRightId(R.id.matrix_app_layout_0);
        this.matrix_movie_layout_3.setNextFocusLeftId(R.id.matrix_movie_layout_2);
        this.matrix_movie_layout_3.setNextFocusDownId(R.id.matrix_movie_layout_6);

        this.matrix_movie_layout_4.setNextFocusUpId(R.id.matrix_movie_layout_1);
        this.matrix_movie_layout_4.setNextFocusRightId(R.id.matrix_movie_layout_2);
        this.matrix_movie_layout_4.setNextFocusLeftId(R.id.matrix_movie_layout_0);
        this.matrix_movie_layout_4.setNextFocusDownId(R.id.matrix_movie_layout_7);

        this.matrix_movie_layout_5.setNextFocusUpId(R.id.matrix_movie_layout_2);
        this.matrix_movie_layout_5.setNextFocusRightId(R.id.matrix_movie_layout_6);
        this.matrix_movie_layout_5.setNextFocusLeftId(R.id.matrix_movie_layout_7);
        this.matrix_movie_layout_5.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

        this.matrix_movie_layout_6.setNextFocusUpId(R.id.matrix_movie_layout_3);
        this.matrix_movie_layout_6.setNextFocusRightId(R.id.matrix_app_layout_3);
        this.matrix_movie_layout_6.setNextFocusLeftId(R.id.matrix_movie_layout_5);
        this.matrix_movie_layout_6.setNextFocusDownId(R.id.sub_navigation_home_external_text);

        this.matrix_movie_layout_7.setNextFocusUpId(R.id.matrix_movie_layout_4);
        this.matrix_movie_layout_7.setNextFocusRightId(R.id.matrix_movie_layout_5);
        this.matrix_movie_layout_7.setNextFocusLeftId(R.id.matrix_movie_layout_0);
        this.matrix_movie_layout_7.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);
        super.init();
    }

    public void loadData(boolean isRefleshData) {
        LogUtils.d("loadData() called");
        setMatrixData(isRefleshData);
    }

    class MyOnTouchListener implements OnTouchListener {
        MyOnTouchListener(MatrixMovieView arg1) {
            super();
        }

        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                doOnClick(view);

            }
            return false;

        }


    }

    class movieLayoutClickListener implements View.OnClickListener {
        movieLayoutClickListener(MatrixMovieView arg1) {
            super();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "===== onClick()");
            doOnClick(v);
/*		int id = v.getId();
        Log.d(TAG,"===== onClick(), id = " + id);


		 if (id == R.id.matrix_movie_layout_0) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("org.chromium.caster_receiver_apk",
					"org.chromium.caster_receiver_apk.TvMainActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);  
		  }else if (id == R.id.matrix_movie_layout_1) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("org.chromium.caster_receiver_apk",
					"org.chromium.caster_receiver_apk.TvMainActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
	   }else if (id == R.id.matrix_movie_layout_2) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("org.chromium.caster_receiver_apk",
					"org.chromium.caster_receiver_apk.TvMainActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent); 
	   }else if (id == R.id.matrix_movie_layout_3) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("org.chromium.caster_receiver_apk",
					"org.chromium.caster_receiver_apk.TvMainActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);  
	   }else if (id == R.id.matrix_movie_layout_4) {
   		Intent SettingsIntent = new Intent();
			SettingsIntent.setComponent(new ComponentName("org.chromium.caster_receiver_apk",
					"org.chromium.caster_receiver_apk.TvMainActivity"));
			SettingsIntent.setAction("android.intent.action.MAIN");
			mContext.startActivity(SettingsIntent);  
	   }*/
        }
    }

    ArrayList movieList;

    public void setMatrixData(boolean isRefleshData) {
        MatrixMovieView.this.movieList = MatrixMovieView.this.movieDAO.queryMatrixMovie(null, null, null, null);
        int v0 = this.movieList.size();
        Log.d("zzktag", "setMatrixData() called size =" + v0);
        RelativeLayout.LayoutParams v7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        int v2;
        for (v2 = 0; v2 < v0; ++v2) {
            Object v9 = this.movieList.get(v2);
            int v1 = ((ArrayList) v9).size();
            int v10 = this.flippers[v2].getChildCount();
            int v3;
            if (v1 > v10) {

                for (v3 = 0; v3 < v1 - v10; ++v3) {
                    this.flippers[v2].addView(new AppRecommendView(this.getContext()), v7);
                }
            } else if (v1 < v10) {
                for (v3 = v1; v3 < v10; ++v3) {
                    this.flippers[v2].removeViewAt(v3);
                }
            }

            if (((ArrayList) v9).size() > 0) {
                this.flippers[v2].setFlipInterval(5000);
            }

            int v4;
            for (v4 = 0; v4 < v1; ++v4) {
                Object v5 = ((ArrayList) v9).get(v4);
                Object v6 = null;
                View v8 = this.flippers[v2].getChildAt(v4);
                if (((AppRecommendView) v8).getTag() != null) {
                    v6 = ((AppRecommendView) v8).getTag();
                }
                Log.d("zzktag", "setMatrixData() v6 =" + v6);
                if (v6 == null || ((IconsEntity) v6).get_appID() != ((IconsEntity) v5).get_appID() || !((IconsEntity) v6).get_icon().equals(((IconsEntity) v5).get_icon())) {
                    ((AppRecommendView) v8).setTag(v5);
                    this.mapDownload.put(((IconsEntity) v5).get_appName(), v8);


                }
                ImageLoader.getInstance().displayImage(((IconsEntity) v5).get_icon(), ((AppRecommendView) v8).getImageView(), isRefleshData);
            }
        }

    }

    public void doOnClick(View view) {
        int indet = -1;
        int tmp = 0;
        while (tmp < MatrixMovieView.this.movieLayouts.length) {
            if (view == MatrixMovieView.this.movieLayouts[tmp]) {
                indet = tmp;
            } else {
                ++tmp;
                continue;
            }
            break;
        }

        if (indet < 0 || indet >= MatrixMovieView.this.flippers.length) {

        } else if (MatrixMovieView.this.flippers[indet].getCurrentView() != null && MatrixMovieView.this.flippers[indet].getChildCount() != 0) {
            Object obj = MatrixMovieView.this.flippers[indet].getCurrentView().getTag();
            if (obj != null) {
                try {
                    String params = ((IconsEntity) obj).get_params();
                    JSONObject dataJson = new JSONObject(params);
                    int startType = ((IconsEntity) obj).get_startType();
                    String action = ((IconsEntity) obj).get_action();
                    if (1 == startType) {
                        String url = dataJson.getString("uri");
                        Intent intentChannel = new Intent(action, Uri.parse(url));
                        mContext.startActivity(intentChannel);

                    } else {
                        String packageName = ((IconsEntity) obj).get_packageName();
                        String className = ((IconsEntity) obj).get_className();
                        Intent startIntent = new Intent();
                        startIntent.setComponent(new ComponentName(packageName, className));
                        startIntent.setAction(action);
                        Iterator<String> iterator = dataJson.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            String value = dataJson.getString(key);
                            startIntent.putExtra(key, value);
                        }
                        mContext.startActivity(startIntent);
                    }
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
        new MyDialog(mContext, iconInfo, "影视") {
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
                Log.d(TAG, "dialog choise=");

            }
        };

    }

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

