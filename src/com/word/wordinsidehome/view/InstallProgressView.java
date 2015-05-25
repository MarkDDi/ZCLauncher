package com.word.wordinsidehome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.word.wordinsidehome.R;

public class InstallProgressView extends View {
    AnimationDrawable mAnimationDrawable;

    public InstallProgressView(Context context) {
        super(context);
        this.init();
    }

    public InstallProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public InstallProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {

        this.setBackgroundResource(R.anim.animation_winside_loading);
        this.mAnimationDrawable = (AnimationDrawable)this.getBackground();
        this.mAnimationDrawable.stop();
    }

    public void setAnimation(boolean flag) {
        if(flag) {
            this.mAnimationDrawable.start();
        }
        else {
            this.mAnimationDrawable.stop();
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(visibility == VISIBLE) {
            this.mAnimationDrawable.start();
        }
        else {
            if(this.mAnimationDrawable != null) {
                this.mAnimationDrawable.stop();
            }

            this.clearAnimation();
        }
    }
}

