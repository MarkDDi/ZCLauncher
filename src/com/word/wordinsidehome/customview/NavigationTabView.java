package com.word.wordinsidehome.customview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.*;
import android.animation.*;
import android.util.Log;

import com.word.wordinsidehome.R;


public class NavigationTabView extends RelativeLayout {
    public interface onNavigationTabChangeListener {
        void onScrollComplete(int arg1);

        void onScrollStart(int arg1);
    }

    private final int DURATION = 600;
    private final int TRANSX = 109;
    private ImageView appFadeView;
    private AnimatorSet appGetFocusAnimSet;
    private AnimatorSet appLostFocusAnimSet;
    private TextView appTextView;
    private ImageView cursorImageView;
    private Animator cursorImageViewAnimator;
    private ImageView gameFadeView;
    private AnimatorSet gameGetFocusAnimSet;
    private AnimatorSet gameLostFocusAnimSet;
    private AnimatorSet healthGetFocusAnimSet;
    private AnimatorSet healthLostFocusAnimSet;
    private TextView gameTextView;
    private ImageView movieFadeView;
    private TextView healthTextView;
    private ImageView healthFadeView;
    private AnimatorSet movieGetFocusAnimSet;
    private AnimatorSet movieLostFocusAnimSet;
    private TextView movieTextView;
    private AnimatorSet recomendGetFocusAnimSet;
    private AnimatorSet recomendLostFocusAnimSet;
    private ImageView recommendFadeView;
    private TextView recommendTextView;
    private onNavigationTabChangeListener tabChangeListener;
    private ImageView educationFadeView;
    private AnimatorSet educationGetFocusAnimSet;
    private AnimatorSet educationLostFocusAnimSet;
    private TextView educationTextView;

    public NavigationTabView(Context context) {
        super(context);
        this.init();
    }

    public NavigationTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public NavigationTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }


    @SuppressLint("NewApi")
    public void clearCursorAnimation() {
        if (this.cursorImageViewAnimator != null) {
            this.cursorImageViewAnimator.end();
            this.cursorImageViewAnimator = null;
        }
    }

    @SuppressLint("NewApi")
    private void init() {
        this.addView(NavigationTabView.inflate(this.getContext(), R.layout.top_navigation_tab_view, null), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        this.gameTextView = (TextView) this.findViewById(R.id.navigation_tab_game_text);
        this.healthTextView = (TextView) this.findViewById(R.id.navigation_tab_health_text);
        this.educationTextView = (TextView) this.findViewById(R.id.navigation_tab_education_text);
        this.recommendTextView = (TextView) this.findViewById(R.id.navigation_tab_recommend_text);
        this.movieTextView = (TextView) this.findViewById(R.id.navigation_tab_movie_text);
        this.appTextView = (TextView) this.findViewById(R.id.navigation_tab_app_text);
        this.gameFadeView = (ImageView) this.findViewById(R.id.navigation_tab_game_fade);
        this.healthFadeView = (ImageView) this.findViewById(R.id.navigation_tab_health_fade);
        this.educationFadeView = (ImageView) this.findViewById(R.id.navigation_tab_education_fade);
        this.recommendFadeView = (ImageView) this.findViewById(R.id.navigation_tab_recommend_fade);
        this.movieFadeView = (ImageView) this.findViewById(R.id.navigation_tab_movie_fade);
        this.appFadeView = (ImageView) this.findViewById(R.id.navigation_tab_app_fade);
        this.cursorImageView = (ImageView) this.findViewById(R.id.navigation_cursor_view);
        this.healthTextView.setNextFocusDownId(R.id.matrix_health_layout_0);
        this.healthTextView.setNextFocusLeftId(R.id.navigation_tab_app_text);
        this.healthTextView.setNextFocusUpId(R.id.bnt_center);
        this.healthTextView.setNextFocusRightId(R.id.navigation_tab_game_text);

        this.gameTextView.setNextFocusDownId(R.id.matrix_game_layout_0);
        this.gameTextView.setNextFocusLeftId(R.id.navigation_tab_health_text);
        this.gameTextView.setNextFocusUpId(R.id.bnt_center);
        this.gameTextView.setNextFocusRightId(R.id.navigation_tab_recommend_text);

        this.educationTextView.setNextFocusDownId(R.id.matrix_education_layout_1);
        this.educationTextView.setNextFocusLeftId(R.id.navigation_tab_recommend_text);
        this.educationTextView.setNextFocusUpId(R.id.bnt_center);
        this.educationTextView.setNextFocusRightId(R.id.navigation_tab_movie_text);

        this.recommendTextView.setNextFocusDownId(R.id.matrix_recommend_layout_0);
        this.recommendTextView.setNextFocusLeftId(R.id.navigation_tab_game_text);
        this.recommendTextView.setNextFocusUpId(R.id.bnt_center);
        this.recommendTextView.setNextFocusRightId(R.id.navigation_tab_education_text);

        this.movieTextView.setNextFocusDownId(R.id.matrix_movie_layout_1);
        this.movieTextView.setNextFocusLeftId(R.id.navigation_tab_education_text);
        this.movieTextView.setNextFocusUpId(R.id.bnt_center);
        this.movieTextView.setNextFocusRightId(R.id.navigation_tab_app_text);

        this.appTextView.setNextFocusDownId(R.id.matrix_app_layout_1);
        this.appTextView.setNextFocusLeftId(R.id.navigation_tab_movie_text);
        this.appTextView.setNextFocusUpId(R.id.bnt_center);
        this.appTextView.setNextFocusRightId(R.id.navigation_tab_health_text);
        this.healthTextView.setFocusable(true);
        this.healthTextView.setFocusableInTouchMode(true);
        this.gameTextView.setFocusable(true);
        this.gameTextView.setFocusableInTouchMode(true);
        this.educationTextView.setFocusable(true);
        this.educationTextView.setFocusableInTouchMode(true);
        this.movieTextView.setFocusable(true);
        this.movieTextView.setFocusableInTouchMode(true);
        this.recommendTextView.setFocusable(true);
        this.recommendTextView.setFocusableInTouchMode(true);
        this.appTextView.setFocusable(true);
        this.appTextView.setFocusableInTouchMode(true);
        this.gameGetFocusAnimSet = new AnimatorSet();
        this.gameGetFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.gameTextView, "scaleX", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.gameTextView, "scaleY", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.gameFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.gameLostFocusAnimSet = new AnimatorSet();
        this.gameLostFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.gameTextView, "scaleX", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.gameTextView, "scaleY", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.gameFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.healthGetFocusAnimSet = new AnimatorSet();
        this.healthGetFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.healthTextView, "scaleX", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.healthTextView, "scaleY", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.healthFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.healthLostFocusAnimSet = new AnimatorSet();
        this.healthLostFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.healthTextView, "scaleX", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.healthTextView, "scaleY", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.healthFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});

        this.movieGetFocusAnimSet = new AnimatorSet();
        this.movieGetFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.movieTextView, "scaleX", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.movieTextView, "scaleY", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.movieFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.movieLostFocusAnimSet = new AnimatorSet();
        this.movieLostFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.movieTextView, "scaleX", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.movieTextView, "scaleY", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.movieFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.educationGetFocusAnimSet = new AnimatorSet();
        this.educationGetFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.educationTextView, "scaleX", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.educationTextView, "scaleY", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.educationFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.educationLostFocusAnimSet = new AnimatorSet();
        this.educationLostFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.educationTextView, "scaleX", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.educationTextView, "scaleY", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.educationFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.recomendGetFocusAnimSet = new AnimatorSet();
        this.recomendGetFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.recommendTextView, "scaleX", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.recommendTextView, "scaleY", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.recommendFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.recomendLostFocusAnimSet = new AnimatorSet();
        this.recomendLostFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.recommendTextView, "scaleX", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.recommendTextView, "scaleY", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.recommendFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.appGetFocusAnimSet = new AnimatorSet();
        this.appGetFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.appTextView, "scaleX", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.appTextView, "scaleY", new float[]{1.0f, 1.2f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.appFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.appLostFocusAnimSet = new AnimatorSet();
        this.appLostFocusAnimSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.appTextView, "scaleX", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.appTextView, "scaleY", new float[]{1.2f, 1.0f}).setDuration(DURATION), ObjectAnimator.ofFloat(this.appFadeView, "alpha", new float[]{1.0f, 0.6f, 1.0f}).setDuration(DURATION)});
        this.gameTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    NavigationTabView.this.gameGetFocusAnimSet.start();
                    NavigationTabView.this.gameFadeView.setVisibility(View.VISIBLE);
                    NavigationTabView.this.tabChangeListener.onScrollStart(((Integer) NavigationTabView.this.gameTextView.getTag()).intValue());
                } else {
                    NavigationTabView.this.gameLostFocusAnimSet.start();
                    NavigationTabView.this.gameFadeView.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.healthTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    NavigationTabView.this.healthGetFocusAnimSet.start();
                    NavigationTabView.this.healthFadeView.setVisibility(View.VISIBLE);
                    NavigationTabView.this.tabChangeListener.onScrollStart(((Integer) NavigationTabView.this.healthTextView.getTag()).intValue());
                } else {
                    NavigationTabView.this.healthLostFocusAnimSet.start();
                    NavigationTabView.this.healthFadeView.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.educationTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    Log.d("zzk", "educationTextView focused");
                    NavigationTabView.this.educationGetFocusAnimSet.start();
                    NavigationTabView.this.educationFadeView.setVisibility(View.VISIBLE);
                    NavigationTabView.this.tabChangeListener.onScrollStart(((Integer) NavigationTabView.this.educationTextView.getTag()).intValue());
                } else {
                    NavigationTabView.this.educationLostFocusAnimSet.start();
                    NavigationTabView.this.educationFadeView.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.recommendTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    NavigationTabView.this.recomendGetFocusAnimSet.start();
                    NavigationTabView.this.recommendFadeView.setVisibility(View.VISIBLE);
                    Log.d("zzk", "recommendTextView focused");

                    NavigationTabView.this.tabChangeListener.onScrollStart(((Integer) NavigationTabView.this.recommendTextView.getTag()).intValue());
                } else {
                    Log.d("zzk", "recommendTextView lost focused");
                    NavigationTabView.this.recomendLostFocusAnimSet.start();
                    NavigationTabView.this.recommendFadeView.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.movieTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    NavigationTabView.this.movieGetFocusAnimSet.start();
                    NavigationTabView.this.movieFadeView.setVisibility(View.VISIBLE);
                    NavigationTabView.this.tabChangeListener.onScrollStart(((Integer) (NavigationTabView.this.movieTextView.getTag())).intValue());
                } else {
                    NavigationTabView.this.movieLostFocusAnimSet.start();
                    NavigationTabView.this.movieFadeView.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.appTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    NavigationTabView.this.appGetFocusAnimSet.start();
                    NavigationTabView.this.appFadeView.setVisibility(View.VISIBLE);
                    NavigationTabView.this.tabChangeListener.onScrollStart(((Integer) NavigationTabView.this.appTextView.getTag()).intValue());
                } else {
                    NavigationTabView.this.appLostFocusAnimSet.start();
                    NavigationTabView.this.appFadeView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void scrollCursor(int preIndex, int currentIndex, int pageCount) {
        float[]   v4;
        ImageView v2;
        long      v10 = 0;
        float     v9  = 109f;
        int       v8  = 2;
        int       v1  = preIndex - currentIndex;
        if (v1 != 0) {
            float v0 = this.cursorImageView.getTranslationX();
            if (currentIndex == pageCount - 1 && preIndex == 0) {
                v2 = this.cursorImageView;
                v4 = new float[v8];
                v4[0] = v0;
                v4[1] = (((float) ((pageCount - 1) * TRANSX))) + v0;
                this.cursorImageViewAnimator = ObjectAnimator.ofFloat(v2, "translationX", v4).setDuration(v10);
                this.cursorImageViewAnimator.start();
                return;
            }

            if (currentIndex == 0 && preIndex == pageCount - 1) {
                v2 = this.cursorImageView;
                v4 = new float[v8];
                v4[0] = v0;
                v4[1] = v0 - (((float) ((pageCount - 1) * TRANSX)));
                this.cursorImageViewAnimator = ObjectAnimator.ofFloat(v2, "translationX", v4).setDuration(v10);
                this.cursorImageViewAnimator.start();
                return;
            }

            if (v1 > 0) {
                v2 = this.cursorImageView;
                v4 = new float[v8];
                v4[0] = v0;
                v4[1] = v0 - v9;
                this.cursorImageViewAnimator = ObjectAnimator.ofFloat(v2, "translationX", v4).setDuration(DURATION);
                this.cursorImageViewAnimator.start();
            }

            if (v1 >= 0) {
                return;
            }

            v2 = this.cursorImageView;
            v4 = new float[v8];
            v4[0] = v0;
            v4[1] = v0 + v9;
            this.cursorImageViewAnimator = ObjectAnimator.ofFloat(v2, "translationX", v4).setDuration(DURATION);
            this.cursorImageViewAnimator.start();
        }
    }

public void setAppTabVisble(int visble, int tag) {
        this.appTextView.setVisibility(visble);
        this.appTextView.setTag(Integer.valueOf(tag));
    }

    public void setHealthTabVisble(int visble, int tag) {
        this.healthTextView.setVisibility(visble);
        this.healthTextView.setTag(Integer.valueOf(tag));
    }

    public void setCurrentItem(int index, boolean withFocus) {
    }

    public void setGameTabVisble(int visble, int tag) {
        this.gameTextView.setVisibility(visble);
        this.gameTextView.setTag(Integer.valueOf(tag));
    }

    public void setIndexRequestFocus(int index) {
        switch (index) {
            case 0: {

                this.healthTextView.requestFocus();
                break;
            }
            case 1: {

                this.gameTextView.requestFocus();
                break;
            }
            case 2: {
                Log.d("zzk", "recommendTextView=" + recommendTextView);

                this.recommendTextView.requestFocus();
                break;
            }
            case 3: {
                this.educationTextView.requestFocus();
                break;
            }
            case 4: {
                this.movieTextView.requestFocus();
                break;
            }
            case 5: {
                this.appTextView.requestFocus();
                break;
            }
        }
    }

    public void setMovieTabVisble(int visble, int tag) {
        this.movieTextView.setVisibility(visble);
        this.movieTextView.setTag(Integer.valueOf(tag));
    }

    public void setOnNavigationTabChangeListener(onNavigationTabChangeListener tabChangeListener) {
        this.tabChangeListener = tabChangeListener;
    }

    public void setRecommandTabVisble(int visble, int tag) {
        Log.d("zzk", "setRecommandTabVisble visble =" + visble);
        this.recommendTextView.setVisibility(visble);
        this.recommendTextView.setTag(Integer.valueOf(tag));
    }

    public void setEducationTabVisble(int visble, int tag) {
        this.educationTextView.setVisibility(visble);
        this.educationTextView.setTag(Integer.valueOf(tag));

    }
}

