package com.word.wordinsidehome.customview.pager3d;

import android.annotation.SuppressLint;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.*;
import android.animation.*;


@SuppressLint(value={"NewApi"}) public class PageChangeAnimationFactory {
    protected final float DOUBLE_NEGATIVE_UNIT_ANGLE;
    protected final float DOUBLE_UNIT_ANGLE;
    protected final float NEGATIVE_UNIT_ANGLE;
    protected final float SIDE_ALPHA;
    protected final float UNIT_ANGLE;
    private MatrixRotateYAnimation _0ToNegUnit2ImmediatelyRotateYAnimation;
    private MatrixRotateYAnimation  _0ToNegUnit3ImmediatelyRotateYAnimation;
    private MatrixRotateYAnimation _0ToNegUnitImmediatelyRotateYAnimation;
    private MatrixRotateYAnimation _0ToNegUnitRotateYAnimation;
    private MatrixRotateYAnimation _0ToUnit2ImmediatelyRotateYAnimation;
    private MatrixRotateYAnimation  _0ToUnit3ImmediatelyRotateYAnimation;
    private MatrixRotateYAnimation _0ToUnitImmediatelyRotateYAnimation;
    private MatrixRotateYAnimation _0ToUnitRotateYAnimation;
    private MatrixRotateYAnimation _2UnitToNeg2UnitRotateYAnimation;
    private MatrixRotateYAnimation    _3UnitToNeg3UnitRotateYAnimation;
    private MatrixRotateYAnimation _2UnitToUnitRotateYAnimation;
    private int duration;
    private MatrixRotateYAnimation neg2UnitTo2UnitRotateYAnimation;
    private MatrixRotateYAnimation neg3UnitToUnitRotateYAnimation;
    private MatrixRotateYAnimation neg2UnitToUnitRotateYAnimation;
    private MatrixRotateYAnimation negUnitRotateYAnimation;
    private MatrixRotateYAnimation negUnitTo0RotateYAnimation;
    private MatrixRotateYAnimation negUnitToNeg2UnitRotateYAnimation;
    private MatrixRotateYAnimation unit2RotateYAnimation;
    private MatrixRotateYAnimation unitTo0RotateYAnimation;
    private MatrixRotateYAnimation unitTo2UnitRotateYAnimation;
    private MatrixRotateYAnimation  unitTo3UnitRotateYAnimation;

    public PageChangeAnimationFactory() {
        super();
        this.duration = 600;
        this.UNIT_ANGLE = 33f;
        this.DOUBLE_UNIT_ANGLE = 66f;
        this.NEGATIVE_UNIT_ANGLE = -33f;
        this.DOUBLE_NEGATIVE_UNIT_ANGLE = -66f;
        this.SIDE_ALPHA = 0.3f;
        this._0ToUnitRotateYAnimation = null;
        this.unitTo0RotateYAnimation = null;
        this._0ToUnitImmediatelyRotateYAnimation = null;
        this.negUnitRotateYAnimation = null;
        this.negUnitTo0RotateYAnimation = null;
        this._0ToNegUnitImmediatelyRotateYAnimation = null;
        this._0ToNegUnit2ImmediatelyRotateYAnimation = null;
        this._0ToNegUnit3ImmediatelyRotateYAnimation=null;
        this._0ToNegUnitRotateYAnimation = null;
        this.negUnitToNeg2UnitRotateYAnimation = null;
        this.neg2UnitToUnitRotateYAnimation = null;
        this._2UnitToUnitRotateYAnimation = null;
        this.unitTo2UnitRotateYAnimation = null;
        this.unitTo3UnitRotateYAnimation =null;
        this._2UnitToNeg2UnitRotateYAnimation = null;
         this._3UnitToNeg3UnitRotateYAnimation =null;
        this.neg2UnitTo2UnitRotateYAnimation = null;
        this.unit2RotateYAnimation = null;
        this._0ToUnit2ImmediatelyRotateYAnimation = null;
        this._0ToUnit3ImmediatelyRotateYAnimation =null;
    }

    protected MatrixRotateYAnimation createRotateYAnimation(float fromDegree, float toDegree, boolean 
            isImmediately) {
        MatrixRotateYAnimation v0 = new MatrixRotateYAnimation(fromDegree, toDegree);
        v0.setFillAfter(true);
        int v1 = isImmediately ? 0 : this.duration;
        v0.setDuration(((long)v1));
        return v0;
    }

    public void get0ToNegUnit2ImmediatelyRotateAnimation(TabBasePageView pageView) {
        if(this._0ToNegUnit2ImmediatelyRotateYAnimation == null) {
            this._0ToNegUnit2ImmediatelyRotateYAnimation = this.createRotateYAnimation(0f, -66f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 
                0.93f}).setDuration(0), this._0ToNegUnit2ImmediatelyRotateYAnimation);
    }
//zzk add
    public void get0ToNegUnit3ImmediatelyRotateAnimation(TabBasePageView pageView) {
        if(this._0ToNegUnit3ImmediatelyRotateYAnimation == null) {
            this._0ToNegUnit3ImmediatelyRotateYAnimation = this.createRotateYAnimation(0f, -99f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 
                0.93f}).setDuration(0), this._0ToNegUnit3ImmediatelyRotateYAnimation);
    }
    
    public void get0ToNegUnitImmediatelyRotateAnimation(TabBasePageView pageView) {
        long v7 = 0;
        int v5 = 2;
        if(this._0ToNegUnitImmediatelyRotateYAnimation == null) {
            this._0ToNegUnitImmediatelyRotateYAnimation = this.createRotateYAnimation(0f, -33f, true);
        }

        AnimatorSet v2 = new AnimatorSet();
        ObjectAnimator v0 = ObjectAnimator.ofFloat(pageView, "alpha", new float[]{1f, 0.3f}).setDuration(
                v7);
        ObjectAnimator v1 = ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 0.93f}).setDuration(
                v7);
        Animator[] v3 = new Animator[v5];
        v3[0] = v0;
        v3[1] = v1;
        v2.playTogether(v3);
        pageView.setPageViewChangeAnimator(((Animator)v2), this._0ToNegUnitImmediatelyRotateYAnimation);
    }

    public void get0ToNegUnitRotateAnimation(TabBasePageView pageView) {
        int v6 = 2;
        if(this._0ToNegUnitRotateYAnimation == null) {
            this._0ToNegUnitRotateYAnimation = this.createRotateYAnimation(0f, -33f, false);
        }

        this._0ToNegUnitRotateYAnimation.setAnimationListener(null);
        AnimatorSet v2 = new AnimatorSet();
        ObjectAnimator v0 = ObjectAnimator.ofFloat(pageView, "alpha", new float[]{1f, 0.3f}).setDuration(((
                long)this.duration));
        ObjectAnimator v1 = ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 0.93f}).setDuration(((
                long)this.duration));
        Animator[] v3 = new Animator[v6];
        v3[0] = v0;
        v3[1] = v1;
        v2.playTogether(v3);
        pageView.setPageViewChangeAnimator(((Animator)v2), this._0ToNegUnitRotateYAnimation);
    }

    public void get0ToUnit2ImmediatelyRotateYAnimation(TabBasePageView pageView) {
        if(this._0ToUnit2ImmediatelyRotateYAnimation == null) {
            this._0ToUnit2ImmediatelyRotateYAnimation = this.createRotateYAnimation(0f, 66f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 
                0.93f}).setDuration(0), this._0ToUnit2ImmediatelyRotateYAnimation);
    }
//zzk add
    public void get0ToUnit3ImmediatelyRotateYAnimation(TabBasePageView pageView) {
        if(this._0ToUnit3ImmediatelyRotateYAnimation == null) {
            this._0ToUnit3ImmediatelyRotateYAnimation = this.createRotateYAnimation(0f, 99f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 
                0.93f}).setDuration(0), this._0ToUnit3ImmediatelyRotateYAnimation);
    }
    
    public void get0ToUnitImmediatelyAnimation(TabBasePageView pageView) {
        long v7 = 0;
        int v5 = 2;
        if(this._0ToUnitImmediatelyRotateYAnimation == null) {
            this._0ToUnitImmediatelyRotateYAnimation = this.createRotateYAnimation(0f, 33f, true);
        }

        AnimatorSet v2 = new AnimatorSet();
        ObjectAnimator v0 = ObjectAnimator.ofFloat(pageView, "alpha", new float[]{1f, 0.3f}).setDuration(
                v7);
        ObjectAnimator v1 = ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 0.93f}).setDuration(
                v7);
        Animator[] v3 = new Animator[v5];
        v3[0] = v0;
        v3[1] = v1;
        v2.playTogether(v3);
        pageView.setPageViewChangeAnimator(((Animator)v2), this._0ToUnitImmediatelyRotateYAnimation);
    }

    public void get0ToUnitRotateAnimation(TabBasePageView pageView) {
        int v6 = 2;
        if(this._0ToUnitRotateYAnimation == null) {
            this._0ToUnitRotateYAnimation = this.createRotateYAnimation(0f, 33f, false);
        }

        AnimatorSet v2 = new AnimatorSet();
        ObjectAnimator v0 = ObjectAnimator.ofFloat(pageView, "alpha", new float[]{1f, 0.3f}).setDuration(((
                long)this.duration));
        ObjectAnimator v1 = ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{1f, 0.93f}).setDuration(((
                long)this.duration));
        Animator[] v3 = new Animator[v6];
        v3[0] = v0;
        v3[1] = v1;
        v2.playTogether(v3);
        pageView.setPageViewChangeAnimator(((Animator)v2), this._0ToUnitRotateYAnimation);
    }



    public void get2UnitToNeg2UnitRotateYAnimation(TabBasePageView pageView) {
        if(this._2UnitToNeg2UnitRotateYAnimation == null) {
            this._2UnitToNeg2UnitRotateYAnimation = this.createRotateYAnimation(-66f, -99f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(null, this._2UnitToNeg2UnitRotateYAnimation);
    }
    
     public void get3UnitToNeg3UnitRotateYAnimation(TabBasePageView pageView) {
        if(this._3UnitToNeg3UnitRotateYAnimation == null) {
            this._3UnitToNeg3UnitRotateYAnimation = this.createRotateYAnimation(-99f, 66f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(null, this._3UnitToNeg3UnitRotateYAnimation);
    }
 
    public void get2UnitToUnitRotateYAnimation(TabBasePageView pageView) {
        if(this._2UnitToUnitRotateYAnimation == null) {
            this._2UnitToUnitRotateYAnimation = this.createRotateYAnimation(66f, 33f, false);
        }

        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "alpha", new float[]{0f, 
                0.3f}).setDuration(((long)this.duration)), this._2UnitToUnitRotateYAnimation);
    }

    public void getNeg2UnitTo2UnitRotateYAnimation(TabBasePageView pageView) {
        if(this.neg2UnitTo2UnitRotateYAnimation == null) {
            this.neg2UnitTo2UnitRotateYAnimation = this.createRotateYAnimation(-66f, 66f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(null, this.neg2UnitTo2UnitRotateYAnimation);
    }

    public void getNeg3UnitToNegUnitRotateYAnimation(TabBasePageView pageView) {
        if(this.neg3UnitToUnitRotateYAnimation == null) {
            this.neg3UnitToUnitRotateYAnimation = this.createRotateYAnimation(-99f, -66f, true);
        }
       pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(null, this.neg3UnitToUnitRotateYAnimation);
    }
    
    public void getNeg2UnitToNegUnitRotateYAnimation(TabBasePageView pageView) {
        if(this.neg2UnitToUnitRotateYAnimation == null) {
            this.neg2UnitToUnitRotateYAnimation = this.createRotateYAnimation(-66f, -33f, false);
        }

        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "alpha", new float[]{0f, 
                0.3f}).setDuration(((long)this.duration)), this.neg2UnitToUnitRotateYAnimation);
    }

    public MatrixRotateYAnimation getNegUnitRotateAnimation() {
        if(this.negUnitRotateYAnimation == null) {
            this.negUnitRotateYAnimation = this.createRotateYAnimation(0f, -33f, false);
        }

        return this.negUnitRotateYAnimation;
    }

    public void getNegUnitTo0RotateAnimation(TabBasePageView pageView) {
        int v6 = 2;
        pageView.bringToFront();
        if(this.negUnitTo0RotateYAnimation == null) {
            this.negUnitTo0RotateYAnimation = this.createRotateYAnimation(-33f, 0f, false);
        }

        AnimatorSet v2 = new AnimatorSet();
        ObjectAnimator v0 = ObjectAnimator.ofFloat(pageView, "alpha", new float[]{0.3f, 1f}).setDuration(((
                long)this.duration));
        ObjectAnimator v1 = ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{0.93f, 1f}).setDuration(((
                long)this.duration));
        Animator[] v3 = new Animator[v6];
        v3[0] = v0;
        v3[1] = v1;
        v2.playTogether(v3);
        pageView.setPageViewChangeAnimator(((Animator)v2), this.negUnitTo0RotateYAnimation);
    }

    public void getNegUnitToNeg2UnitRotateYAnimation(TabBasePageView pageView) {
        if(this.negUnitToNeg2UnitRotateYAnimation == null) {
            this.negUnitToNeg2UnitRotateYAnimation = this.createRotateYAnimation(-33f, -66f, false);
        }

        this.negUnitToNeg2UnitRotateYAnimation.setAnimationListener(null);
        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "alpha", new float[]{0.3f, 
                0f}).setDuration(((long)(this.duration / 2))), this.negUnitToNeg2UnitRotateYAnimation);
    }

    public void getNegUnitToNeg2UnitRotateYAnimation(TabBasePageView pageView, Animation.AnimationListener 
            listener) {
        if(this.negUnitToNeg2UnitRotateYAnimation == null) {
            this.negUnitToNeg2UnitRotateYAnimation = this.createRotateYAnimation(-33f, -66f, false);
        }

        this.negUnitToNeg2UnitRotateYAnimation.setAnimationListener(listener);
        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "alpha", new float[]{0.3f, 
                0f}).setDuration(((long)(this.duration / 2))), this.negUnitToNeg2UnitRotateYAnimation);
    }

    public MatrixRotateYAnimation getUnit2RotateYAnimation() {
        if(this.unit2RotateYAnimation == null) {
            this.unit2RotateYAnimation = this.createRotateYAnimation(0f, 66f, false);
        }

        return this.unit2RotateYAnimation;
    }

    public void getUnitTo0RotateAnimation(TabBasePageView pageView) {
    	   pageView.bringToFront();
        int v6 = 2;
        if(this.unitTo0RotateYAnimation == null) {
            this.unitTo0RotateYAnimation = this.createRotateYAnimation(33f, 0f, false);
        }

        AnimatorSet v2 = new AnimatorSet();
        ObjectAnimator v0 = ObjectAnimator.ofFloat(pageView, "alpha", new float[]{0.3f, 1f}).setDuration(((
                long)this.duration));
        ObjectAnimator v1 = ObjectAnimator.ofFloat(pageView, "scaleY", new float[]{0.93f, 1f}).setDuration(((
                long)this.duration));
        Animator[] v3 = new Animator[v6];
        v3[0] = v0;
        v3[1] = v1;
        v2.playTogether(v3);
        pageView.setPageViewChangeAnimator(((Animator)v2), this.unitTo0RotateYAnimation);
    }

    public void getUnitTo2UnitRotateYAnimation(TabBasePageView pageView) {
        if(this.unitTo2UnitRotateYAnimation == null) {
            this.unitTo2UnitRotateYAnimation = this.createRotateYAnimation(33f, 66f, false);
        }

        pageView.setPageViewChangeAnimator(ObjectAnimator.ofFloat(pageView, "alpha", new float[]{0.3f, 
                0f}).setDuration(((long)(this.duration / 2))), (Animation)this.unitTo2UnitRotateYAnimation);
    }

    public void getUnitTo3UnitRotateYAnimation(TabBasePageView pageView) {
        if(this.unitTo3UnitRotateYAnimation == null) {
            this.unitTo3UnitRotateYAnimation = this.createRotateYAnimation(66f, -99f, true);
        }

        pageView.setAlpha(0f);
        pageView.setPageViewChangeAnimator(null, this.unitTo3UnitRotateYAnimation);
    }
}

