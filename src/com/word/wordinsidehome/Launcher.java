package com.word.wordinsidehome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.word.wordinsidehome.customview.NavigationTabView;
import com.word.wordinsidehome.customview.NavigationTabView.onNavigationTabChangeListener;
import com.word.wordinsidehome.customview.pager3d.OnPageViewChangeListener;
import com.word.wordinsidehome.customview.pager3d.SixPageViewStrategy;
import com.word.wordinsidehome.customview.pager3d.View3DPager;
import com.word.wordinsidehome.service.RefleshHandler;
import com.word.wordinsidehome.service.dao.LauncherDAO;
import com.word.wordinsidehome.service.entity.IconsEntity;
import com.word.wordinsidehome.service.entity.LauncherEntity;
import com.word.wordinsidehome.service.image.ImageLoader;
import com.word.wordinsidehome.utils.LogUtils;
import com.word.wordinsidehome.view.LauncerUpdateDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Launcher extends Activity implements OnClickListener {

    private final static String TAG = "Wordinsidehome";
    private final Context mContext = this;
    private boolean is24hFormart = false;
    private final String SD_PATH = "/storage/external_storage/sdcard1";
    private final String USB_PATH = "/storage/external_storage";
    private final String net_change_action = "android.net.conn.CONNECTIVITY_CHANGE";
    private final String wifi_signal_action = "android.net.wifi.RSSI_CHANGED";
    public static final String weather_request_action = "android.windInside.launcher.REQUEST_WEATHER";
    private final String weather_receive_action = "android.amlogic.settings.WEATHER_INFO";
    public static final String PREFERENCE_WINSIDE_SETTING = "preference_winsideLauncher_settings";
    public static final String WINSIDE_LAUNCHER_VERSION = "winside_launcher_version";
    private static int time_count = 0;
    private final int time_freq = 10;
    private RelativeLayout layoutSport;
    private RelativeLayout layoutEducation;
    private RelativeLayout layoutApp;
    private RelativeLayout layoutGame;
    private RelativeLayout layoutMovie;
    private View sub_navigation_home_app_install_text;
    private View sub_navigation_home_setting_text;
    private View sub_navigation_home_external_text;
    private TextView appTextView;
    private TextView movieTextView;
    private TextView educationTextView;
    private TextView recommendTextView;
    private TextView gameTextView;
    private TextView healthTextView;
    private GridView lv_status;
    private ImageView img_wifi;

    private int mCurrentViewIndex;
    private View3DPager matrixPager;
    private NavigationTabView navigationTabView;
    private View v_blackbackground;
    private RelativeLayout RelativeLayout_Navigation = null;
    private RelativeLayout relativeLayout_top_navigation_view;
    private Button bnt_center;
    private ImageView img_logo;
    private LauncherDAO launcherDAO;
    private Animation showNavigationBarAction;
    private Animation hideNavigationBarAction;
    private int current_focused_top_navigation_id;

    private boolean Navigationbar_show_flag = false;
    private boolean shouldShowLauncherUpdateTip = true;
    private RefleshHandler refleshHandler;
    private GestureDetector gestureDetector;
    private SharedPreferences sharepreference = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initChildViews();
        LogUtils.d( "onCreate()");
        gestureDetector = new GestureDetector(Launcher.this, onGestureListener);
        sharepreference = getSharedPreferences(PREFERENCE_WINSIDE_SETTING, Context.MODE_PRIVATE);
        refleshHandler = new RefleshHandler(this);
        // 启动服务（数据刷新）
        refleshHandler.startRefleshImmediately();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_EJECT);
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        filter.addDataScheme("file");
        registerReceiver(mediaReceiver, filter);

        filter = new IntentFilter();
        filter.addAction(net_change_action);
        filter.addAction(wifi_signal_action);
        // 监听wifi便携式热点状态广播，该广播为系统隐藏广播，无法直接使用，需要在源码环境下编译
//        filter.addAction(WifiManager.WIFI_AP_STATE_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(weather_receive_action);
        registerReceiver(netReceiver, filter);

        filter = new IntentFilter();
        // 该广播由LoadService服务发出
        filter.addAction("DATA_ACTION");
        this.registerReceiver(dataReceiver, filter);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * 底部三个导航的焦点控制
     * @param mCurrentIndex ViewPager当前的index
     */
    private void handleSubTab(int mCurrentIndex) {
        switch (mCurrentIndex) {
            case 0:   //app

                this.sub_navigation_home_app_install_text.setNextFocusUpId(R.id.matrix_app_layout_4);
                this.sub_navigation_home_app_install_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_app_install_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_app_install_text.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

                this.sub_navigation_home_setting_text.setNextFocusUpId(R.id.matrix_app_layout_3);
                this.sub_navigation_home_setting_text.setNextFocusRightId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_setting_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_setting_text.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

                this.sub_navigation_home_external_text.setNextFocusUpId(R.id.matrix_app_layout_5);
                this.sub_navigation_home_external_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_external_text.setNextFocusLeftId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_external_text.setNextFocusDownId(R.id.sub_navigation_home_external_text);

                break;
            case 1:

                this.sub_navigation_home_app_install_text.setNextFocusUpId(R.id.matrix_movie_layout_5);
                this.sub_navigation_home_app_install_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_app_install_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_app_install_text.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

                this.sub_navigation_home_setting_text.setNextFocusUpId(R.id.matrix_movie_layout_0);
                this.sub_navigation_home_setting_text.setNextFocusRightId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_setting_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_setting_text.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

                this.sub_navigation_home_external_text.setNextFocusUpId(R.id.matrix_movie_layout_6);
                this.sub_navigation_home_external_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_external_text.setNextFocusLeftId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_external_text.setNextFocusDownId(R.id.sub_navigation_home_external_text);
                break;

            case 2:
                this.sub_navigation_home_app_install_text.setNextFocusUpId(R.id.matrix_education_layout_4);
                this.sub_navigation_home_app_install_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_app_install_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_app_install_text.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

                this.sub_navigation_home_setting_text.setNextFocusUpId(R.id.matrix_education_layout_0);
                this.sub_navigation_home_setting_text.setNextFocusRightId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_setting_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_setting_text.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

                this.sub_navigation_home_external_text.setNextFocusUpId(R.id.matrix_education_layout_7);
                this.sub_navigation_home_external_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_external_text.setNextFocusLeftId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_external_text.setNextFocusDownId(R.id.sub_navigation_home_external_text);
                break;
            case 3:
                this.sub_navigation_home_app_install_text.setNextFocusUpId(R.id.matrix_recommend_layout_4);
                this.sub_navigation_home_app_install_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_app_install_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_app_install_text.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

                this.sub_navigation_home_setting_text.setNextFocusUpId(R.id.matrix_recommend_layout_3);
                this.sub_navigation_home_setting_text.setNextFocusRightId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_setting_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_setting_text.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

                this.sub_navigation_home_external_text.setNextFocusUpId(R.id.matrix_recommend_layout_5);
                this.sub_navigation_home_external_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_external_text.setNextFocusLeftId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_external_text.setNextFocusDownId(R.id.sub_navigation_home_external_text);
                break;
            case 4:
                this.sub_navigation_home_app_install_text.setNextFocusUpId(R.id.matrix_game_layout_4);
                this.sub_navigation_home_app_install_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_app_install_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_app_install_text.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

                this.sub_navigation_home_setting_text.setNextFocusUpId(R.id.matrix_game_layout_0);
                this.sub_navigation_home_setting_text.setNextFocusRightId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_setting_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_setting_text.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

                this.sub_navigation_home_external_text.setNextFocusUpId(R.id.matrix_game_layout_6);
                this.sub_navigation_home_external_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_external_text.setNextFocusLeftId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_external_text.setNextFocusDownId(R.id.sub_navigation_home_external_text);
                break;

            case 5:
                this.sub_navigation_home_app_install_text.setNextFocusUpId(R.id.matrix_health_layout_5);
                this.sub_navigation_home_app_install_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_app_install_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_app_install_text.setNextFocusDownId(R.id.sub_navigation_home_app_install_text);

                this.sub_navigation_home_setting_text.setNextFocusUpId(R.id.matrix_health_layout_0);
                this.sub_navigation_home_setting_text.setNextFocusRightId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_setting_text.setNextFocusLeftId(R.id.sub_navigation_home_setting_text);
                this.sub_navigation_home_setting_text.setNextFocusDownId(R.id.sub_navigation_home_setting_text);

                this.sub_navigation_home_external_text.setNextFocusUpId(R.id.matrix_health_layout_7);
                this.sub_navigation_home_external_text.setNextFocusRightId(R.id.sub_navigation_home_external_text);
                this.sub_navigation_home_external_text.setNextFocusLeftId(R.id.sub_navigation_home_app_install_text);
                this.sub_navigation_home_external_text.setNextFocusDownId(R.id.sub_navigation_home_external_text);
                break;


            default:
                break;
        }

    }

    private void ShowNavigationBar() {
        LogUtils.d( "ShowNavigationBar");
        if ((Navigationbar_show_flag == false)) {
            RelativeLayout_Navigation.startAnimation(showNavigationBarAction);
            RelativeLayout_Navigation.bringToFront();
            RelativeLayout_Navigation.setVisibility(View.VISIBLE);
            Navigationbar_show_flag = true;

        }

    }

    void hideNavigationBar() {
        if (Navigationbar_show_flag) {
            ((TextView) this.findViewById(current_focused_top_navigation_id)).requestFocus();
            RelativeLayout_Navigation.startAnimation(hideNavigationBarAction);
            RelativeLayout_Navigation.setVisibility(View.INVISIBLE);
            Navigationbar_show_flag = false;
        }
    }

    private void initChildViews() {
        int defaultIndex = 2;
        showNavigationBarAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showNavigationBarAction.setDuration(300);

        hideNavigationBarAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        hideNavigationBarAction.setDuration(300);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        this.launcherDAO = new LauncherDAO(mContext);
        img_logo.setBackgroundResource(R.drawable.logo);
        bnt_center = (Button) findViewById(R.id.bnt_center);

        bnt_center.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                hideNavigationBar();
                Intent fileIntent = new Intent();
                fileIntent.setComponent(new ComponentName("com.winside.usercenter", "com.winside.usercenter.MainActivity"));
                fileIntent.setAction("android.intent.action.MAIN");
                Launcher.this.startActivity(fileIntent);

            }
        });
        bnt_center.setNextFocusDownId(R.id.navigation_tab_health_text);
        bnt_center.setNextFocusLeftId(R.id.bnt_center);
        bnt_center.setNextFocusUpId(R.id.bnt_center);
        bnt_center.setNextFocusRightId(R.id.bnt_center);
        appTextView = (TextView) findViewById(R.id.navigation_tab_app_text);
        movieTextView = (TextView) findViewById(R.id.navigation_tab_movie_text);
        educationTextView = (TextView) this.findViewById(R.id.navigation_tab_education_text);
        recommendTextView = (TextView) this.findViewById(R.id.navigation_tab_recommend_text);
        gameTextView = (TextView) this.findViewById(R.id.navigation_tab_game_text);
        healthTextView = (TextView) this.findViewById(R.id.navigation_tab_health_text);

        RelativeLayout_Navigation = (RelativeLayout) findViewById(R.id.RelativeLayoutNavigation);
        relativeLayout_top_navigation_view = (RelativeLayout) findViewById(R.id.relativeLayout_top_navigation_view);
        sub_navigation_home_app_install_text = (RelativeLayout) this.findViewById(R.id.sub_navigation_home_app_install_text);
        sub_navigation_home_app_install_text.setOnClickListener(this);
        sub_navigation_home_setting_text = (RelativeLayout) this.findViewById(R.id.sub_navigation_home_setting_text);
        sub_navigation_home_setting_text.setOnClickListener(this);
        sub_navigation_home_external_text = (RelativeLayout) this.findViewById(R.id.sub_navigation_home_external_text);
        sub_navigation_home_external_text.setOnClickListener(this);
        this.matrixPager = (View3DPager) this.findViewById(R.id.matrix_pager_view);
        this.matrixPager.setLongClickable(true);
        this.matrixPager.setOnTouchListener(new MyGestureListener(this));
        this.navigationTabView = (NavigationTabView) this.findViewById(R.id.navigation_tab_view);


        this.matrixPager.initTabViewByIndex(defaultIndex, new SixPageViewStrategy(this.matrixPager));
        this.matrixPager.setTopAndSubTabs(this.navigationTabView, null);
        // 由每个不同的内容页去加载自己重写后的loadData
        Launcher.this.matrixPager.loadData(false);
        LauncherEntity launcherEntity = launcherDAO.queryLauncherInfo(null, null, null, null);

        LogUtils.e("查询LauncherInfo : " + launcherEntity);
        if (launcherEntity != null) {
            ImageLoader.getInstance().displayImage(launcherEntity.get_icon(), img_logo, true);

        }


        this.matrixPager.setPageViewChangeListener(new OnPageViewChangeListener() {
            public void onPageChangeComplete(int mCurrentIndex) {
                String v0;
                Launcher.this.mCurrentViewIndex = mCurrentIndex;
                if (3 == mCurrentIndex) {
                    LogUtils.i("onMatrixPageChangeComplete-->sendBroad");
                    Launcher.this.sendBroadcast(new Intent("com.hiveview.tv.view.hdmiin.small_show"));
                    //   AppScene.setScene("com.hiveview.tv.view.MaxtrTvView");
                    Log.i("Launcher", "onMatrixPageChangeComplete-->end");
                } else {
                    // AppScene.setScene("com.hiveview.tv.activity.Launcher");
                    Launcher.this.sendBroadcast(new Intent("com.hiveview.tv.view.hdmiin.small_unshow"));
                }

                Launcher.this.navigationTabView.invalidate();
                Launcher.this.matrixPager.invalidateAllPageView();
                //  Builder v1 = new Builder(Launcher.this.getBaseContext()).setTabNo(Tab.DEFAULT).setViewPosition(
                //         "0301");


                //  KeyEventHandler.post(v1.setPositionId(v0).setTabNo(Tab.TAB).setDataType(null).build());
            }

            public void onPageChangeStart(int mCurrentIndex) {
                LogUtils.d( "===== onPageChangeStart(), mCurrentIndex = " + mCurrentIndex);
                handleSubTab(mCurrentIndex);
                // Launcher.this.subTabView.setCurrentItem(mCurrentIndex);
            }
        });
        this.navigationTabView.setOnNavigationTabChangeListener(new onNavigationTabChangeListener() {
            public void onScrollComplete(int currentIndex) {
            }

            public void onScrollStart(int targetIndex) {
                if (Launcher.this.matrixPager.getCurrentPageIndex() == Launcher.this.matrixPager.get3DPagerChildCount() - 1 && targetIndex == 0) {
                    LogUtils.d("===== (), moveToNext ");
                    Launcher.this.matrixPager.moveToNext();
                    return;
                }

                if (Launcher.this.matrixPager.getCurrentPageIndex() == 0 && targetIndex == Launcher
                        .this.matrixPager.get3DPagerChildCount() - 1) {
                    Launcher.this.matrixPager.moveToPrevious();
                    return;
                }

                if (Launcher.this.matrixPager.getCurrentPageIndex() - targetIndex > 0) {
                    Launcher.this.matrixPager.moveToPrevious();
                    return;
                }

                if (Launcher.this.matrixPager.getCurrentPageIndex() - targetIndex < 0) {
                    LogUtils.d("===== ()6666, getCurrentPageIndex =" + Launcher.this.matrixPager
                            .getCurrentPageIndex() + "  targetIndex=" + targetIndex);
                    Launcher.this.matrixPager.moveToNext();
                }
            }
        });
        this.navigationTabView.setIndexRequestFocus(defaultIndex);

        img_wifi = (ImageView) findViewById(R.id.img_wifi);
        lv_status = (GridView) findViewById(R.id.list_status);
/*    layoutSport = (RelativeLayout)findViewById(R.id.matrix_home_layout_0);
    layoutSport.setOnClickListener(this);
    layoutGame = (RelativeLayout)findViewById(R.id.matrix_home_layout_1);
    layoutGame.setOnClickListener(this);
    layoutEducation = (RelativeLayout)findViewById(R.id.matrix_home_layout_2);
    layoutEducation.setOnClickListener(this);
    layoutMovie = (RelativeLayout)findViewById(R.id.matrix_home_layout_3);
    layoutMovie.setOnClickListener(this);
    layoutApp = (RelativeLayout)findViewById(R.id.matrix_home_layout_4);
    layoutApp.setOnClickListener(this);*/
        this.v_blackbackground = (View) findViewById(R.id.v_blackbackground);
        this.v_blackbackground.postDelayed(new Runnable() {
            public void run() {
                Launcher.this.v_blackbackground.setVisibility(View.GONE);
            }
        }, 2000);
    }


    // Launcher界面时屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean v0 = keyCode == KeyEvent.KEYCODE_BACK ? true : super.onKeyDown(keyCode, event);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                LogUtils.d( "KEYCODE_DPAD_UP");
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                //hideNavigationBar();
                LogUtils.d( "KEYCODE_DPAD_DOWN");
                break;

        }

        return v0;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        int v0;

        if (this.matrixPager.findFocus() != null && event.getAction() == KeyEvent.ACTION_DOWN) {
            v0 = this.matrixPager.findFocus().getId();
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && (v0 == R.id.matrix_recommend_layout_2 || v0 == R.id.matrix_recommend_layout_6 || v0 == R.id.matrix_game_layout_3 || v0 == R.id.matrix_game_layout_5 || v0 == R.id.matrix_game_layout_6 || v0 == R.id.matrix_health_layout_3 || v0 == R.id.matrix_health_layout_6 || v0 == R.id.matrix_health_layout_7 || v0 == R.id.matrix_education_layout_3 || v0 == R.id.matrix_education_layout_6 || v0 == R.id.matrix_education_layout_7 || v0 == R.id.matrix_movie_layout_3 || v0 == R.id.matrix_movie_layout_6 || v0 == R.id.matrix_app_layout_2)) {
                this.matrixPager.moveToPrevious();
                return false;

            }
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && (v0 == R.id.matrix_recommend_layout_0 || v0 == R.id.matrix_recommend_layout_3 || v0 == R.id.matrix_game_layout_0 || v0 == R.id.matrix_health_layout_0 || v0 == R.id.matrix_education_layout_0 || v0 == R.id.matrix_movie_layout_0 || v0 == R.id.matrix_app_layout_0 || v0 == R.id.matrix_app_layout_3)) {
                this.matrixPager.moveToNext();
                return false;

            }


        }
        if (this.relativeLayout_top_navigation_view.findFocus() != null && event.getAction() == KeyEvent.ACTION_DOWN) {
            v0 = this.relativeLayout_top_navigation_view.findFocus().getId();
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP && (v0 == R.id.navigation_tab_health_text || v0 == R.id.navigation_tab_game_text || v0 == R.id.navigation_tab_recommend_text || v0 == R.id.navigation_tab_education_text || v0 == R.id.navigation_tab_movie_text || v0 == R.id.navigation_tab_app_text)) {
                current_focused_top_navigation_id = v0;
                LogUtils.d( "setNextFocusDownId id =" + v0);
                ShowNavigationBar();
                return false;

            }

        }
        if (this.bnt_center.findFocus() != null && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                hideNavigationBar();
                return true;

            }

        }
        return super.dispatchKeyEvent(event);

    }

    /* public boolean onTouchEvent(MotionEvent event) {
             LogUtils.d("===== onTouchEvent()" );
            return gestureDetector.onTouchEvent(event);
     }  */
    private GestureDetector.OnGestureListener onGestureListener = new SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();

            if (x > 0) {
                //  doResult(RIGHT);
                LogUtils.d( "===== onGestureListener() right");
            } else if (x < 0) {
                //  doResult(LEFT);
                LogUtils.d( "===== onGestureListener() left");
            }
            return true;
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        LogUtils.d( "===== onClick(), id = " + id);

        if (v instanceof RelativeLayout) {


            if (id == R.id.sub_navigation_home_app_install_text) {

                Intent SettingsIntent = new Intent();
                SettingsIntent.setComponent(new ComponentName("com.word.wordinsideapp", "com.word.wordinsideapp.MainActivity"));

                SettingsIntent.setAction("android.intent.action.MAIN");
                this.startActivity(SettingsIntent);

            } else if (id == R.id.sub_navigation_home_setting_text) {
                Intent SettingsIntent = new Intent();
                SettingsIntent.setComponent(new ComponentName("com.mbx.settingsmbox", "com.mbx.settingsmbox.SettingsMboxActivity"));

                SettingsIntent.setAction("android.intent.action.MAIN");
                this.startActivity(SettingsIntent);

            } else if (id == R.id.sub_navigation_home_external_text) {

                Intent fileIntent = new Intent();
                fileIntent.setComponent(new ComponentName("com.fb.FileBrower", "com.fb.FileBrower.FileBrower"));
                fileIntent.setAction("android.intent.action.MAIN");
                this.startActivity(fileIntent);

		/*	Intent fileIntent = new Intent();
            fileIntent.setComponent(new ComponentName("com.xiaobaifile.tv",
					"com.xiaobaifile.tv.view.StartupActivity"));		
			fileIntent.setAction("android.intent.action.MAIN");
			this.startActivity(fileIntent); 		 */

            } else {

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayDate();
        displayStatus();
        // ImageView img_weather = (ImageView)findViewById(R.id.img_weather);
        // img_weather.setImageResource(R.drawable.sunny03);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        LogUtils.d( "------onDestroy");
        unregisterReceiver(mediaReceiver);
        unregisterReceiver(netReceiver);
        unregisterReceiver(dataReceiver);

        super.onDestroy();

    }

    @SuppressLint("NewApi")
    private void displayDate() {

        is24hFormart = DateFormat.is24HourFormat(this);
        TextView time = (TextView) findViewById(R.id.tx_time);
        time.setText(getDate() + " " + getTime());
        time.setTypeface(Typeface.DEFAULT_BOLD);

    }

    private String getDate() {
        final Calendar c = Calendar.getInstance();
        int int_Month = c.get(Calendar.MONTH);
        String mDay = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        int int_Week = c.get(Calendar.DAY_OF_WEEK) - 1;
        String str_week = this.getResources().getStringArray(R.array.week)[int_Week];
        String mMonth = this.getResources().getStringArray(R.array.month)[int_Month];

        String date;
        if (Locale.getDefault().getLanguage().equals("zh")) {
            date = mMonth + " " + mDay + this.getResources().getString(R.string.str_day);

        } else {

            date = mMonth + " " + mDay;
        }

//LogUtils.d( "@@@@@@@@@@@@@@@@@@@ "+ date  + "week = " +int_Week);
        return date;
    }

    public String getTime() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String time = "";

        if (hour >= 10) {
            time += Integer.toString(hour);
        } else {
            time += "0" + Integer.toString(hour);
        }
        time += ":";

        if (minute >= 10) {
            time += Integer.toString(minute);
        } else {
            time += "0" + Integer.toString(minute);
        }

        return time;
    }

    private void sendWeatherBroadcast() {
        Intent intent = new Intent();
        // 发送到weather包的WeatherReceiver接收
        intent.setAction(weather_request_action);
        sendBroadcast(intent);
    }

    private void displayStatus() {
        LogUtils.d( "displayStatus is called----------------------------");
        WifiManager mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        int wifi_rssi = mWifiInfo.getRssi();
        int wifi_level = WifiManager.calculateSignalLevel(wifi_rssi, 5);
        switch (wifi_level) {
            case 0:
                img_wifi.setImageResource(R.drawable.wifi1);
                break;
            case 1:
                img_wifi.setImageResource(R.drawable.wifi2);
                break;
            case 2:
                img_wifi.setImageResource(R.drawable.wifi3);
                break;
            case 3:
                img_wifi.setImageResource(R.drawable.wifi4);
                break;
            case 4:
                img_wifi.setImageResource(R.drawable.wifi5);
                break;
            default:
                break;
        }


        LocalAdapter ad = new LocalAdapter(Launcher.this, getStatusData(isEthernetOn()), R.layout.homelist_item, new String[]{"item_type", "item_name", "item_sel"}, new int[]{R.id.item_type, 0, 0});
        lv_status.setAdapter(ad);
    }

    private void updateStatus() {
        ((BaseAdapter) lv_status.getAdapter()).notifyDataSetChanged();
    }

    public List<Map<String, Object>> getStatusData(boolean is_ethernet_on) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

	/*	 if(isSdcardExists() == true){
             map = new HashMap<String, Object>();
			map.put("item_type", R.drawable.img_status_sdcard);
			list.add(map);
		}*/

        if (isUsbExists() == true) {
            map = new HashMap<String, Object>();
            map.put("item_type", R.drawable.img_status_usb);
            list.add(map);
        }

        if (is_ethernet_on == true) {
            //	map = new HashMap<String, Object>();
            //	map.put("item_type", R.drawable.img_status_ethernet);
            img_wifi.setImageResource(R.drawable.img_status_ethernet);
            //	list.add(map);
            LogUtils.d( "R.drawable.img_status_ethernet-------------------------------");
        }

        return list;
    }

    private boolean isUsbExists() {
        File dir = new File(USB_PATH);
        if (dir.exists() && dir.isDirectory()) {
            if (dir.listFiles() != null && dir.listFiles().length > 0) {
                    for (File file : dir.listFiles()) {
                        String path = file.getAbsolutePath();
                        if (path.startsWith(USB_PATH + "/sd") && !path.equals(SD_PATH)) {
                            return true;
                        }
                    }
            }
        }

        return false;
    }

    private boolean isSdcardExists() {
        if (Environment.getExternalStorageState().startsWith(Environment.MEDIA_MOUNTED)) {
            File dir = new File(SD_PATH);
            if (dir.exists() && dir.isDirectory()) {
                return true;
            }
        }
        return false;
    }

    private boolean isEthernetOn() {
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
       /* NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);

        if (info.isConnected()) {
            return true;
        } else {
            return false;
        }*/
        return true;
    }

    private void setWeatherView(String str_weather) {
        if (str_weather == null || str_weather.length() == 0) {
            return;
        }

        String[] list_data = str_weather.split(",");
        ImageView img_weather = (ImageView) findViewById(R.id.img_weather);
        img_weather.setVisibility(View.VISIBLE);
        if (list_data.length >= 3 && list_data[2] != null)
            img_weather.setImageResource(parseIcon(list_data[2]));
    }

    private int parseIcon(String strIcon) {
        if (strIcon == null) return -1;
        if ("baoxue".equals(strIcon)) return R.drawable.baoxue;
        if ("baoyu".equals(strIcon)) return R.drawable.baoyu;
        if ("baoyuezhuantedabaoyu".equals(strIcon)) return R.drawable.baoyuezhuantedabaoyu;
        if ("baoyuzhuandabaoyu".equals(strIcon)) return R.drawable.baoyuzhuandabaoyu;
        if ("dabaoyu".equals(strIcon)) return R.drawable.dabaoyu;
        if ("daxue".equals(strIcon)) return R.drawable.daxue;
        if ("daxuezhuandabaoxue".equals(strIcon)) return R.drawable.daxuezhuandabaoxue;
        if ("dayu".equals(strIcon)) return R.drawable.dayu;
        if ("dayuzhuanbaoyu".equals(strIcon)) return R.drawable.dayuzhuanbaoyu;
        if ("dongyu".equals(strIcon)) return R.drawable.dongyu;
        if ("duoyun".equals(strIcon)) return R.drawable.duoyun;
        if ("fuchen".equals(strIcon)) return R.drawable.fuchen;
        if ("leizhenyu".equals(strIcon)) return R.drawable.leizhenyu;
        if ("mai".equals(strIcon)) return R.drawable.mai;
        if ("qiangshachenbao".equals(strIcon)) return R.drawable.qiangshachenbao;
        if ("qing".equals(strIcon)) return R.drawable.qing;
        if ("shachenbao".equals(strIcon)) return R.drawable.shachenbao;
        if ("tedabaoyu".equals(strIcon)) return R.drawable.tedabaoyu;
        if ("wu".equals(strIcon)) return R.drawable.wu;
        if ("xiaoxue".equals(strIcon)) return R.drawable.xiaoxue;
        if ("xiaoxuezhuanzhongxue".equals(strIcon)) return R.drawable.xiaoxuezhuanzhongxue;
        if ("xiaoyu".equals(strIcon)) return R.drawable.xiaoyu;
        if ("xiaoyuzhuanzhongyu".equals(strIcon)) return R.drawable.xiaoyuzhuanzhongyu;
        if ("yangsha".equals(strIcon)) return R.drawable.yangsha;
        if ("yin".equals(strIcon)) return R.drawable.yin;
        if ("yujiaxue".equals(strIcon)) return R.drawable.yujiaxue;
        if ("zhenxue".equals(strIcon)) return R.drawable.zhenxue;
        if ("zhenyu".equals(strIcon)) return R.drawable.zhenyu;
        if ("zhenyuyoubingbao".equals(strIcon)) return R.drawable.zhenyuyoubingbao;
        if ("zhongxue".equals(strIcon)) return R.drawable.zhongxue;
        if ("zhongxuezhuandaxue".equals(strIcon)) return R.drawable.zhongxuezhuandaxue;
        if ("zhongyu".equals(strIcon)) return R.drawable.zhongyu;
        if ("zhongyuzhuandayu".equals(strIcon)) return R.drawable.zhongyuzhuandayu;
        return 0;
    }

    private BroadcastReceiver mediaReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //LogUtils.d( " mediaReceiver		  action = " + action);
            if (action == null) return;

            if (Intent.ACTION_MEDIA_EJECT.equals(action) || Intent.ACTION_MEDIA_UNMOUNTED.equals(action) || Intent.ACTION_MEDIA_MOUNTED.equals(action)) {

                displayStatus();
                updateStatus();

            }
            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {


            }

        }
    };
    private BroadcastReceiver dataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent mIntent) {
            // 接收LoadService发送过来的广播，在将数据进行持久化后发送
            if (mIntent.getAction().equals("DATA_ACTION")) {
                LogUtils.d("BroadcastReceiver DATA_ACTION");
                Launcher.this.matrixPager.loadData(true);
                //load logo
                LauncherEntity launcherEntity = launcherDAO.queryLauncherInfo(null, null, null, null);


                if (launcherEntity != null) {
                    String version = sharepreference.getString(WINSIDE_LAUNCHER_VERSION, "null");
                    LogUtils.d("launcher version = " + version);
                    LogUtils.e("new versionCode = " + launcherEntity.get_version());
                    // 此处应该是比较版本号大小，后期需要修改服务器发过来的数据
                    if (!version.equals(launcherEntity.get_version()) && shouldShowLauncherUpdateTip) {
                        LogUtils.d("launcherEntity = " + launcherEntity);
                        shouldShowLauncherUpdateTip = false;
                        ImageLoader.getInstance().displayImage(launcherEntity.get_icon(), img_logo, true);
//                        showLauncherNewVersionDialog(launcherEntity);
                    }
                }
            }

        }
    };

    private BroadcastReceiver netReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action == null) return;

            LogUtils.d("netReceiver         action = " + action);

            // 时间广播，每分钟由系统发出一次
            if (action.equals(Intent.ACTION_TIME_TICK)) {
                displayDate();

                time_count++;
                // 每10分钟发送一次天气广播
                if (time_count >= time_freq) {
                    // 发送到WeatherReceiver
                    sendWeatherBroadcast();
                    time_count = 0;
                }
            } else if (action.equals(weather_receive_action)) { // 该广播由WeatherBroadCastThread类发出
                String weatherInfo = intent.getExtras().getString("weather_today");
                // weatherInfo：深圳,30 ~ 26℃,duoyun
                LogUtils.d( "@@@@@@@@@@@@@@@@@@@@@@@@@@ receive " + action + " weather:" + weatherInfo);
                setWeatherView(weatherInfo);
            } else {
                displayStatus();
                updateStatus();
            }

        }
    };

    private class MyGestureListener extends GestureListener {
        public MyGestureListener(Context context) {
            super(context);
        }

        @Override
        public boolean left() {
            Launcher.this.matrixPager.moveToPrevious();
            switch (Launcher.this.matrixPager.getCurrentPageIndex()) {
                case 0:   //app
                    appTextView.requestFocus();

                    break;
                case 1:   //movie
                    movieTextView.requestFocus();

                    break;
                case 2:   //education
                    educationTextView.requestFocus();

                    break;
                case 3:   //recommend
                    recommendTextView.requestFocus();

                    break;
                case 4:   //game
                    gameTextView.requestFocus();
                    break;
                case 5:   //health
                    healthTextView.requestFocus();
                    break;
            }

            Log.e("test", "to left CurrentPageIndex =" + Launcher.this.matrixPager.getCurrentPageIndex());


            return super.left();
        }

        @Override
        public boolean right() {

            Launcher.this.matrixPager.moveToNext();
            switch (Launcher.this.matrixPager.getCurrentPageIndex()) {
                case 0:   //app
                    appTextView.requestFocus();

                    break;
                case 1:   //movie
                    movieTextView.requestFocus();

                    break;
                case 2:   //education
                    educationTextView.requestFocus();

                    break;
                case 3:   //recommend
                    recommendTextView.requestFocus();

                    break;
                case 4:   //game
                    gameTextView.requestFocus();
                    break;
                case 5:   //health
                    healthTextView.requestFocus();
                    Log.e("test", "healthTextView.requestFocus()");
                    break;
            }
            Log.e("test", "to right CurrentPageIndex =" + Launcher.this.matrixPager.getCurrentPageIndex());


            return super.right();
        }
    }

    private class GestureListener extends SimpleOnGestureListener implements OnTouchListener {
        /**
         * 左右滑动的最短距离
         */
        private int distance = 100;
        /**
         * 左右滑动的最大速度
         */
        private int velocity = 200;

        private GestureDetector gestureDetector;

        @SuppressLint("NewApi")
        public GestureListener(Context context) {
            super();
            gestureDetector = new GestureDetector(context, this);
        }

        /**
         * 向左滑的时候调用的方法，子类应该重写
         *
         * @return
         */
        public boolean left() {
            return false;
        }

        /**
         * 向右滑的时候调用的方法，子类应该重写
         *
         * @return
         */
        public boolean right() {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // e1：第1个ACTION_DOWN MotionEvent
            // e2：最后一个ACTION_MOVE MotionEvent
            // velocityX：X轴上的移动速度（像素/秒）
            // velocityY：Y轴上的移动速度（像素/秒）

            // 向左滑
            if (e1.getX() - e2.getX() > distance && Math.abs(velocityX) > velocity) {
                left();
            }
            // 向右滑
            if (e2.getX() - e1.getX() > distance && Math.abs(velocityX) > velocity) {
                right();
            }
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return false;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getVelocity() {
            return velocity;
        }

        public void setVelocity(int velocity) {
            this.velocity = velocity;
        }

        public GestureDetector getGestureDetector() {
            return gestureDetector;
        }

        public void setGestureDetector(GestureDetector gestureDetector) {
            this.gestureDetector = gestureDetector;
        }
    }

    LauncerUpdateDialog launcherDlg;

    private void showLauncherNewVersionDialog(LauncherEntity launcherEntity) {
        final IconsEntity miconInfo = new IconsEntity();
        miconInfo.set_apk(launcherEntity.get_apk());
        miconInfo.set_appName(mContext.getResources().getString(R.string.launcher_new_version));
        miconInfo.set_version(launcherEntity.get_version());
        miconInfo.set_description(launcherEntity.get_description());
        if (launcherDlg != null && launcherDlg.isShowing()) {
            Log.d("zzklogo", "launcherDlg has already exit");
            return;
        }
        launcherDlg = new LauncerUpdateDialog(mContext, miconInfo, launcherEntity.get_updateDate()) {
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
                //LogUtils.d("dialog choise=");

            }
        };
        launcherDlg.show();
    }

}
