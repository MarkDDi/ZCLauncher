package com.word.wordinsidehome.customview.pager3d;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.word.wordinsidehome.customview.NavigationTabView;
import com.word.wordinsidehome.customview.SubTabView;
import java.util.ArrayList;
import java.util.List;

 public class View3DPager extends RelativeLayout {
    private final String TAG;
    private boolean isRefleshData;
    private OnPageViewChangeListener pageViewChangeListener;
    private List pageViews;
    private Abstract3DPagerStrategy strategy;
    private SubTabView subTabs;
    private NavigationTabView topTabs;

    public View3DPager(Context context) {
        super(context);
        this.TAG = "View3DPager";
        this.pageViews = new ArrayList();
        this.strategy = null;
        this.pageViewChangeListener = null;
        this.topTabs = null;
        this.subTabs = null;
        this.isRefleshData = false;
    }

    public View3DPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.TAG = "View3DPager";
        this.pageViews = new ArrayList();
        this.strategy = null;
        this.pageViewChangeListener = null;
        this.topTabs = null;
        this.subTabs = null;
        this.isRefleshData = false;
    }

    public View3DPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.TAG = "View3DPager";
        this.pageViews = new ArrayList();
        this.strategy = null;
        this.pageViewChangeListener = null;
        this.topTabs = null;
        this.subTabs = null;
        this.isRefleshData = false;
    }

    public void addTabPageView(TabBasePageView child, int index) {
        child.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        this.addView(((View)child), index);
        child.setTag(Integer.valueOf(index));
        this.pageViews.add(child);
    }

    public int get3DPagerChildCount() {
        return this.pageViews.size();
    }

    public int getCurrentPageIndex() {
        return this.strategy.getCurrentIndex();
    }

    public void initTabViewByIndex(int index, Abstract3DPagerStrategy strategy) {
        this.strategy = strategy;
        if(this.pageViews.size() == 0) {
            Log.e("View3DPager", "please add TabPageView!");
        }
        else {
        	Log.e("View3DPager", "pageViews.size="+this.pageViews.size());
            Object v1 = this.pageViews.get(index);
            if(v1 == null) {
                Log.e("View3DPager", String.valueOf(index) + " TabPageView is null");
            }
            else if(strategy == null) {
                Log.e("View3DPager", "please call setStrategy!");
            }
            else {
                strategy.setPageViews(this.pageViews);
                int v0;
                for(v0 = 0; v0 < this.pageViews.size(); ++v0) {
                    ((TabBasePageView)this.pageViews.get(v0)).setAlpha(0f);
                     Log.e("View3DPager", "index="+v0+"setAlpha(0f)");
                }

                ((TabBasePageView)v1).setAlpha(1f);
                ((TabBasePageView)v1).bringToFront();
                this.invalidate();
                strategy.initLeftView(index);
                strategy.initRightView(index);
            }
        }
    }

    public void invalidateAllPageView() {
        this.invalidate();
        int v0;
        for(v0 = 0; v0 < this.pageViews.size(); ++v0) {
            ((TabBasePageView)this.pageViews.get(v0)).invalidate();
        }
    }

    public void loadData(boolean reflesh) {
        this.strategy.loadPageData(reflesh);
    }

    public void moveToNext() {
        int v0;
        for(v0 = 0; v0 < this.pageViews.size(); ++v0) {
            ((TabBasePageView)this.pageViews.get(v0)).clearAnimation();
        }

        this.topTabs.clearCursorAnimation();
        this.topTabs.scrollCursor(this.strategy.addIndex(this.strategy.getCurrentIndex() + 1), this.
                strategy.getCurrentIndex(), this.pageViews.size());
        this.strategy.stopPageViewCustomAnimation();
        this.strategy.moveToNext();
        if(this.pageViewChangeListener != null) {
            this.pageViewChangeListener.onPageChangeStart(this.strategy.getCurrentIndex());
        }
    }

    public void moveToPrevious() {
        int v0 = 0;
        while(v0 < this.pageViews.size()) {
            ((TabBasePageView)this.pageViews.get(v0)).clearAnimation();
            ++v0;
        }

        this.topTabs.clearCursorAnimation();
        this.topTabs.scrollCursor(this.strategy.subtractIndex(this.strategy.getCurrentIndex() - 1), 
                this.strategy.getCurrentIndex(), this.pageViews.size());
        this.strategy.stopPageViewCustomAnimation();
        this.strategy.moveToPrevious();
        if(this.pageViewChangeListener != null) {
            this.pageViewChangeListener.onPageChangeStart(this.strategy.getCurrentIndex());
        }
    }

    public void setInstallApkCount(int gameCount, int appCount) {
        this.strategy.setInstallApkCount(gameCount, appCount);
    }

    public void setPageViewChangeListener(OnPageViewChangeListener pageViewChangeListener) {
        this.pageViewChangeListener = pageViewChangeListener;
        int v0 = 0;
        while(v0 < this.pageViews.size()) {
            ((TabBasePageView)this.pageViews.get(v0)).setPageViewChangeListener(pageViewChangeListener);
            ((TabBasePageView)this.pageViews.get(v0)).setStrategy(this.strategy);
            ++v0;
        }
    }

    public void setTopAndSubTabs(NavigationTabView top, SubTabView sub) {
        this.strategy.setTopAndSubTabs(top, sub);
        this.topTabs = top;
        this.subTabs = sub;
    }
}

