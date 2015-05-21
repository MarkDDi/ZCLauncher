package com.word.wordinsidehome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.view.LayoutInflater;
import android.graphics.drawable.AnimationDrawable;
import com.word.wordinsidehome.R;

public class AppRecommendView extends LinearLayout {
    private ImageView imageView;
    private boolean isUpdateProgress;
    private ImageView appDownloadIcon;

    public AppRecommendView(Context context) {
        super(context);
        this.isUpdateProgress = true;
        this.init();
    }

    public AppRecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isUpdateProgress = true;
    }

    @SuppressLint("NewApi")
    public AppRecommendView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isUpdateProgress = true;
    }

    public ImageView getImageView() {
        this.imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT));
        this.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return this.imageView;
    }


    private void init() {
        this.addView(LayoutInflater.from(this.getContext()).inflate(R.layout.app_recommend_layout, null));
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        this.appDownloadIcon = (ImageView)this.findViewById(R.id.appDownloadIcon);
		this.appDownloadIcon.setBackgroundResource(R.drawable.app_download);
    }




    public void startDownloadAnim() {

        if(this.appDownloadIcon.getVisibility() != VISIBLE) {
            this.appDownloadIcon.setVisibility(VISIBLE);
        }

       AnimationDrawable anim = (AnimationDrawable)appDownloadIcon.getBackground();
	   if(!anim.isRunning()){
	   anim.start();
	   }
    }
    public void stopDownloadAnim() {

        if(this.appDownloadIcon.getVisibility() == VISIBLE) {
            this.appDownloadIcon.setVisibility(INVISIBLE);
        }

       AnimationDrawable anim = (AnimationDrawable)appDownloadIcon.getBackground();
	   if(anim.isRunning()){
	   anim.stop();
	   }
    }	

    public void startUpdateProgress() {
        this.isUpdateProgress = true;
    }


    public void stopUpdateProgress() {
        this.isUpdateProgress = false;
    }
}

