package com.word.wordinsidehome.customview.pager3d;

import android.annotation.SuppressLint;
import com.word.wordinsidehome.customview.NavigationTabView;
import com.word.wordinsidehome.customview.SubTabView;
import java.util.ArrayList;
import java.util.List;

@SuppressLint(value={"UseSparseArrays"})
public abstract class Abstract3DPagerStrategy {
    protected PageChangeAnimationFactory animationFactory;
    protected int currentIndex;
    private boolean isRotating;
    protected List pageViews;
    protected SubTabView subTabs;
    protected NavigationTabView topTabs;
    protected View3DPager view3dPager;

    public Abstract3DPagerStrategy(View3DPager view3dPager) {
        super();
        this.view3dPager = null;
        this.currentIndex = 2;
        this.pageViews = new ArrayList();
        this.topTabs = null;
        this.subTabs = null;
        this.isRotating = false;
        this.animationFactory = new PageChangeAnimationFactory();
        this.view3dPager = view3dPager;
    }

    protected int addIndex(int index) {
        if(index >= this.pageViews.size()) {
            index -= this.pageViews.size();
        }

        return index;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public abstract void initLeftView(int arg1);

    public abstract void initRightView(int arg1);

    public boolean isRotating() {
        return this.isRotating;
    }

    public abstract void loadPageData(boolean arg1);

    public abstract void moveToNext();

    public abstract void moveToPrevious();

    public void setInstallApkCount(int gameCount, int appCount) {
    }

    public void setPageViews(List arg1) {
        this.pageViews = arg1;
    }

    public void setRotating(boolean isRotating) {
        this.isRotating = isRotating;
    }

    public void setTopAndSubTabs(NavigationTabView top, SubTabView sub) {
        this.topTabs = top;
        this.subTabs = sub;
    }

    public void stopPageViewCustomAnimation() {
        int v0;
        for(v0 = 0; v0 < this.pageViews.size(); ++v0) {
            ((TabBasePageView)(this.pageViews.get(v0))).stopPageViewCustomAnimation();
        }
    }

    protected int subtractIndex(int index) {
        if(index < 0) {
            index += this.pageViews.size();
        }

        return index;
    }
}

