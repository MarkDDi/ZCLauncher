package com.word.wordinsidehome.customview;

import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Matrix;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.AttributeSet;

import java.lang.Character;


public class SubTabView extends RelativeLayout {
    private final static String TAG = "MyRelativeLayout";

    private Context mContext = null;
    private static Rect imgRect;
    private float scalePara = 1.1f;
    private float shortcutScalePara = 1.15f;
    private float framePara = 1.1f;
    private float homeframePara = 1.0f;
    private float defframePara = 1.1f;
    private int animDuration = 110;

    public SubTabView(Context context) {
        super(context);
    }

    public SubTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SubTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCurrentItem(int mIndex) {


    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
