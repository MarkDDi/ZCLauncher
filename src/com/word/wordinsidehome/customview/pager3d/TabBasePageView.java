package com.word.wordinsidehome.customview.pager3d;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.word.wordinsidehome.service.net.HttpTaskManager;

import android.view.animation.Animation.AnimationListener;
import android.view.animation.*;
import android.animation.*;

import java.util.HashMap;
import java.util.Iterator;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;


import android.util.Log;

public abstract class TabBasePageView extends RelativeLayout {
    private GestureDetector gestureDetector;
    private GestureDetector.OnGestureListener onGestureListener;


    class DownloadProgressRecevier extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            Log.d("zzkd", "TabBasePageView onAppStoreReceive()");
            TabBasePageView.this.onAppStoreReceive(intent);
        }
    }

    class RotateAnimationListener implements Animation.AnimationListener {
        private TabBasePageView tabBasePageView;

        RotateAnimationListener(TabBasePageView arg1) {
            super();
            tabBasePageView = arg1;

        }

        public void onAnimationEnd(Animation arg0) {
            tabBasePageView.isRotating = false;
            tabBasePageView.strategy.setRotating(tabBasePageView.isRotating);
            tabBasePageView.startPageViewCustomAnimation();
            if (tabBasePageView.pageViewChangeListener != null) {
                tabBasePageView.pageViewChangeListener.onPageChangeComplete(tabBasePageView.strategy.getCurrentIndex());
            }
        }

        public void onAnimationRepeat(Animation arg0) {
        }

        public void onAnimationStart(Animation arg0) {
            TabBasePageView.this.isRotating = true;
            TabBasePageView.this.strategy.setRotating(TabBasePageView.this.isRotating);
        }
    }

    public interface ViewFocusDirectionListener {
        void setViewsFocusDirection(View[] arg1);
    }

    protected final String TAG;
    private Animator alphaAnimator;
    @SuppressLint(value = {"HandlerLeak"})
    public Handler dataHandler;
    protected ViewFlipper[] flippers;
    protected View[] focusEdgeViews;
    protected boolean isRefleshData;
    private boolean isRotating;
    private OnPageViewChangeListener pageViewChangeListener;
    private RotateAnimationListener rotateAnimationListener;
    protected Abstract3DPagerStrategy strategy;
    protected ViewFocusDirectionListener viewFocusDirectionListener;
    protected HashMap mapDownload;
    private DownloadProgressRecevier recevier;

    public TabBasePageView(Context context) {
        super(context);
        this.mapDownload = new HashMap();
        this.initRecevier();
        this.isRefleshData = false;
        this.isRotating = false;
        this.pageViewChangeListener = null;
        this.strategy = null;
        this.alphaAnimator = null;
        this.rotateAnimationListener = new RotateAnimationListener(this);
        this.flippers = null;
        this.focusEdgeViews = null;
        this.viewFocusDirectionListener = null;

        this.TAG = TabBasePageView.class.getSimpleName();
        this.dataHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                TabBasePageView.this.processData(msg.what);
            }
        };
        //	this.setLongClickable(true);
        //this.setOnTouchListener(new MyGestureListener(context));
        this.onGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e2.getX() - e1.getX();
                float y = e2.getY() - e1.getY();

                if (x > 0) {
                    //  doResult(RIGHT);
                    Log.d(TAG, "===== onGestureListener() right");
                } else if (x < 0) {
                    //  doResult(LEFT);
                    Log.d(TAG, "===== onGestureListener() left");
                }
                return true;
            }
        };
    }

    public TabBasePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mapDownload = new HashMap();
        this.initRecevier();
        this.isRefleshData = false;
        this.isRotating = false;
        this.pageViewChangeListener = null;
        this.strategy = null;
        this.alphaAnimator = null;
        this.rotateAnimationListener = new RotateAnimationListener(this);
        this.flippers = null;
        this.focusEdgeViews = null;
        this.viewFocusDirectionListener = null;

        this.TAG = TabBasePageView.class.getSimpleName();
        this.dataHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                TabBasePageView.this.processData(msg.what);
            }
        };
        this.setLongClickable(true);
        this.setOnTouchListener(new MyGestureListener(context));
        this.onGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e2.getX() - e1.getX();
                float y = e2.getY() - e1.getY();

                if (x > 0) {
                    //  doResult(RIGHT);
                    Log.d(TAG, "===== onGestureListener() right");
                } else if (x < 0) {
                    //  doResult(LEFT);
                    Log.d(TAG, "===== onGestureListener() left");
                }
                return true;
            }
        };
    }

    public TabBasePageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mapDownload = new HashMap();
        this.initRecevier();
        this.isRefleshData = false;
        this.isRotating = false;
        this.pageViewChangeListener = null;
        this.strategy = null;
        this.alphaAnimator = null;
        this.rotateAnimationListener = new RotateAnimationListener(this);
        this.flippers = null;
        this.focusEdgeViews = null;
        this.viewFocusDirectionListener = null;

        this.TAG = TabBasePageView.class.getSimpleName();
        this.dataHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                TabBasePageView.this.processData(msg.what);
            }
        };
        this.setLongClickable(true);
        this.setOnTouchListener(new MyGestureListener(context));
        this.onGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e2.getX() - e1.getX();
                float y = e2.getY() - e1.getY();

                if (x > 0) {
                    //  doResult(RIGHT);
                    Log.d(TAG, "===== onGestureListener() right");
                } else if (x < 0) {
                    //  doResult(LEFT);
                    Log.d(TAG, "===== onGestureListener() left");
                }
                return true;
            }
        };
    }

    public TabBasePageView(Context context, ViewFocusDirectionListener listener) {
        super(context);
        this.mapDownload = new HashMap();
        this.initRecevier();
        this.isRefleshData = false;
        this.isRotating = false;
        this.pageViewChangeListener = null;
        this.strategy = null;
        this.alphaAnimator = null;
        this.rotateAnimationListener = new RotateAnimationListener(this);
        this.flippers = null;
        this.focusEdgeViews = null;
        this.viewFocusDirectionListener = null;

        this.TAG = TabBasePageView.class.getSimpleName();
        this.dataHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                TabBasePageView.this.processData(msg.what);
            }
        };
        this.setLongClickable(true);
        this.setOnTouchListener(new MyGestureListener(context));
        this.onGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e2.getX() - e1.getX();
                float y = e2.getY() - e1.getY();

                if (x > 0) {
                    //  doResult(RIGHT);
                    Log.d(TAG, "===== onGestureListener() right");
                } else if (x < 0) {
                    //  doResult(LEFT);
                    Log.d(TAG, "===== onGestureListener() left");
                }
                return true;
            }
        };
        this.viewFocusDirectionListener = listener;
    }

    private void initRecevier() {
        Log.d("zzkd", "initRecevier()");
        this.recevier = new DownloadProgressRecevier();
        IntentFilter intent = new IntentFilter();
        intent.addAction("com.hiveview.appstore.home.download_start");
        intent.addAction("com.hiveview.appstore.home.download_complete");
        intent.addAction("com.hiveview.appstore.download_failed");
        this.getContext().registerReceiver(this.recevier, intent);
    }

    protected void checkPageIsIdle() {
        while (this.strategy.isRotating()) {

            try {
                Thread.sleep(1200);
            } catch (InterruptedException v0) {
                v0.printStackTrace();
            }
        }

        this.updateUIRefleshData();
    }

    @SuppressLint("NewApi")
    public void clearAnimation() {
        super.clearAnimation();
        if (this.alphaAnimator != null) {
            this.alphaAnimator.cancel();
            this.alphaAnimator = null;
        }
    }

    public View getBottomMenuView() {
        return null;
    }

    protected void init() {
        if (this.viewFocusDirectionListener != null) {
            this.viewFocusDirectionListener.setViewsFocusDirection(this.focusEdgeViews);
        }
    }

    public boolean isRotating() {
        return this.isRotating;
    }

    public void loadData(boolean isRefleshData) {
        this.isRefleshData = isRefleshData;
    }

    protected void processData(int msgWhat) {
    }

    protected void sendLoadDataResultMessage(int msgWhat) {
        this.dataHandler.sendEmptyMessage(msgWhat);
    }

    @SuppressLint("NewApi")
    public void setPageViewChangeAnimator(Animator animator, Animation rotateAnimation) {
        this.clearAnimation();
        this.alphaAnimator = animator;
        if (this.alphaAnimator != null) {
            this.alphaAnimator.start();
        }

        if (rotateAnimation != null) {
            // int v0 = this.getTag().intValue();
            int v0 = 2;
            if (this.strategy != null && this.strategy.getCurrentIndex() == v0) {
                rotateAnimation.setAnimationListener(this.rotateAnimationListener);
            }

            this.startAnimation(rotateAnimation);
        }
    }

    public void setPageViewChangeListener(OnPageViewChangeListener pageViewChangeListener) {
        this.pageViewChangeListener = pageViewChangeListener;
    }

    public void setStrategy(Abstract3DPagerStrategy strategy) {
        this.strategy = strategy;
    }

    public void startPageViewCustomAnimation() {
        if (this.flippers != null) {
            int v0;
            for (v0 = 0; v0 < this.flippers.length; ++v0) {
                if (this.flippers[v0].getChildCount() > 1) {
                    this.flippers[v0].startFlipping();
                    int v1;
                    for (v1 = 0; v1 < this.flippers[v0].getChildCount(); ++v1) {
                        this.flippers[v0].getChildAt(v1).clearAnimation();
                    }
                }
            }
        }
    }

    public void stopPageViewCustomAnimation() {
        if (this.flippers != null) {
            int v0;
            for (v0 = 0; v0 < this.flippers.length; ++v0) {
                this.flippers[v0].stopFlipping();
            }
        }
    }

    protected final void submitRequest(Runnable runnable) {
        if (runnable != null) {
            HttpTaskManager.getInstance().submit(runnable);
        }
    }

    public abstract void onAppStoreReceive(Intent intent);

    public abstract void updateUIRefleshData();

    private class MyGestureListener extends GestureListener {
        public MyGestureListener(Context context) {
            super(context);
        }

        @Override
        public boolean left() {
            Log.e("test", "to left");
            return super.left();
        }

        @Override
        public boolean right() {
            Log.e("test", "to right");
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
}

