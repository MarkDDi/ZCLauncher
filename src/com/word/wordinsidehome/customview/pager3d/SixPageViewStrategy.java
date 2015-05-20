package com.word.wordinsidehome.customview.pager3d;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.word.wordinsidehome.customview.MatrixRecommendView;
import com.word.wordinsidehome.customview.MatrixGameView;
import com.word.wordinsidehome.customview.MatrixHealthView;
import com.word.wordinsidehome.customview.MatrixEducationView;
import com.word.wordinsidehome.customview.MatrixMovieView;
import com.word.wordinsidehome.customview.MatrixAppView;
import com.word.wordinsidehome.customview.NavigationTabView;
import com.word.wordinsidehome.customview.SubTabView;
import com.word.wordinsidehome.customview.pager3d.TabBasePageView.ViewFocusDirectionListener;
import android.util.Log;

@SuppressLint(value={"NewApi"}) public class SixPageViewStrategy extends Abstract3DPagerStrategy {
	private final static String TAG="SixPageViewStrategy";
    class GameRightDirectionFocusAble implements ViewFocusDirectionListener {
        GameRightDirectionFocusAble(SixPageViewStrategy arg1) {
        	  super();
          //  SixPageViewStrategy.this = arg1;
        }

        public void setViewsFocusDirection(View[] views) {

        }
    }

    class RecommendRightDirectionFocusAble implements ViewFocusDirectionListener {
        RecommendRightDirectionFocusAble(SixPageViewStrategy arg1) {
      //      SixPageViewStrategy.this = arg1;
            super();
        }

        public void setViewsFocusDirection(View[] views) {

        }
    }

    public SixPageViewStrategy(View3DPager _3dPager) {
        super(_3dPager);
        this.view3dPager.addTabPageView(new MatrixAppView(this.view3dPager.getContext()), 0);         
        this.view3dPager.addTabPageView(new MatrixMovieView(this.view3dPager.getContext()), 1); 
        this.view3dPager.addTabPageView(new MatrixEducationView(this.view3dPager.getContext()), 2); 
        this.view3dPager.addTabPageView(new MatrixRecommendView(this.view3dPager.getContext()), 3);                             
        this.view3dPager.addTabPageView(new MatrixGameView(this.view3dPager.getContext()), 4);
        this.view3dPager.addTabPageView(new MatrixHealthView(this.view3dPager.getContext()), 5); 


  
       // this.view3dPager.addTabPageView(new MatrixRecommendView(this.view3dPager.getContext(), new RecommendRightDirectionFocusAble(
       //         this)), 2);
       // this.view3dPager.addTabPageView(new MatrixGameView(this.view3dPager.getContext(), new GameRightDirectionFocusAble(
       //         this)), 3);
    }

    public void initLeftView(int index) {
        Object v0 = this.pageViews.get(this.currentIndex - 1);
        this.animationFactory.get0ToUnitImmediatelyAnimation(((TabBasePageView)v0));
        ((TabBasePageView)v0).stopPageViewCustomAnimation();
        Object v1 = this.pageViews.get(this.currentIndex - 2);
        this.animationFactory.get0ToUnit2ImmediatelyRotateYAnimation(((TabBasePageView)v1));
        ((TabBasePageView)v1).stopPageViewCustomAnimation();
    }

    public void initRightView(int index) {
        Object v0 = this.pageViews.get(this.currentIndex + 1);
        this.animationFactory.get0ToNegUnitImmediatelyRotateAnimation(((TabBasePageView)v0));
        ((TabBasePageView)v0).stopPageViewCustomAnimation();
        Object v1 = this.pageViews.get(this.currentIndex + 2);
        this.animationFactory.get0ToNegUnit2ImmediatelyRotateAnimation(((TabBasePageView)v1));
        ((TabBasePageView)v1).stopPageViewCustomAnimation();
        Object v2 = this.pageViews.get(this.currentIndex + 3);
        this.animationFactory.get0ToNegUnit3ImmediatelyRotateAnimation(((TabBasePageView)v2));
        ((TabBasePageView)v2).stopPageViewCustomAnimation();        
    }

    public void loadPageData(boolean isRefleshData) {
        ((TabBasePageView)this.pageViews.get(2)).loadData(isRefleshData);
        ((TabBasePageView)this.pageViews.get(3)).loadData(isRefleshData);
        ((TabBasePageView)this.pageViews.get(1)).loadData(isRefleshData);
        ((TabBasePageView)this.pageViews.get(0)).loadData(isRefleshData);
        ((TabBasePageView)this.pageViews.get(4)).loadData(isRefleshData);
        ((TabBasePageView)this.pageViews.get(5)).loadData(isRefleshData);
  
    }

    public void moveToNext() {
    	  Log.d(TAG,"call moveToNext()");
        this.currentIndex = this.addIndex(this.currentIndex + 1);
        int v1 = this.addIndex(this.currentIndex + 1);
        int v2 = this.addIndex(this.currentIndex + 2);
        int v5 = this.subtractIndex(this.currentIndex - 1);
        int v6 = this.subtractIndex(this.currentIndex - 2);
        int v7 = this.subtractIndex(this.currentIndex - 3);
        this.animationFactory.getNegUnitTo0RotateAnimation((TabBasePageView)this.pageViews.get(this.currentIndex)); //(-33,0)
        this.animationFactory.getNeg2UnitToNegUnitRotateYAnimation((TabBasePageView)this.pageViews.get(v1)); //(-66,-33)
        this.animationFactory.getNeg3UnitToNegUnitRotateYAnimation((TabBasePageView)this.pageViews.get(v2)); //(-99,-66)
        this.animationFactory.get0ToUnitRotateAnimation((TabBasePageView)this.pageViews.get(v5));  //(0,33)
        this.animationFactory.getUnitTo2UnitRotateYAnimation((TabBasePageView)this.pageViews.get(v6)); //(33,66)
        this.animationFactory.getUnitTo3UnitRotateYAnimation((TabBasePageView)this.pageViews.get(v7)); //(66,-99)
    }
    public void moveToPrevious() {
    	   Log.d(TAG,"call moveToPrevious()");
        this.currentIndex = this.subtractIndex(this.currentIndex - 1);
        int v5 = this.subtractIndex(this.currentIndex - 1);
        int v6 = this.subtractIndex(this.currentIndex - 2);
        int v1 = this.addIndex(this.currentIndex + 1);
        int v2 = this.addIndex(this.currentIndex + 2);
        int v3 = this.addIndex(this.currentIndex + 3);
        this.animationFactory.getUnitTo0RotateAnimation((TabBasePageView)this.pageViews.get(this.currentIndex));//(33,0)
        this.animationFactory.get2UnitToUnitRotateYAnimation((TabBasePageView)this.pageViews.get(v5)); //(66,33)
        this.animationFactory.get3UnitToNeg3UnitRotateYAnimation((TabBasePageView)this.pageViews.get(v6)); //(-99,66)
        this.animationFactory.get0ToNegUnitRotateAnimation((TabBasePageView)this.pageViews.get(v1)); //(0,-33)
        this.animationFactory.getNegUnitToNeg2UnitRotateYAnimation((TabBasePageView)this.pageViews.get(v2));//(-33,-66)
        this.animationFactory.get2UnitToNeg2UnitRotateYAnimation((TabBasePageView)this.pageViews.get(v3));//(-66,-99)
    }


    public void setTopAndSubTabs(NavigationTabView top, SubTabView sub) {
        super.setTopAndSubTabs(top, sub);
        this.topTabs.setAppTabVisble(0, 0);
        this.topTabs.setMovieTabVisble(0, 1);
        this.topTabs.setEducationTabVisble(0, 2); 
                

        this.topTabs.setRecommandTabVisble(0, 3);   
        this.topTabs.setGameTabVisble(0, 4);
        this.topTabs.setHealthTabVisble(0, 5);       
             	Log.d("zzk", "call setTopAndSubTabs()");
        //sub.addBottomMenus(this.pageViews, this.currentIndex);
    }
}

