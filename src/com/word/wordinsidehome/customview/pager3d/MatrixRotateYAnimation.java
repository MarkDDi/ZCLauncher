package com.word.wordinsidehome.customview.pager3d;

import android.annotation.SuppressLint;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MatrixRotateYAnimation extends Animation {
    private int halfHeight;
    private int halfWidth;
    private Camera mCamera;
    private final float mFromDegree;
    private final float mToDegree;

    public MatrixRotateYAnimation(float fromDegree, float toDegree) {
        super();
        this.mFromDegree = fromDegree;
        this.mToDegree = toDegree;
        this.mCamera = new Camera();
    }

    @SuppressLint(value={"NewApi"}) protected void applyTransformation(float interpolatedTime, Transformation 
            t) {
        Matrix v1 = t.getMatrix();
        float v0 = this.mFromDegree + (this.mToDegree - this.mFromDegree) * interpolatedTime;
        this.mCamera.save();
        this.mCamera.translate(0f, 0f, ((float)(this.halfWidth * 3)));
        this.mCamera.rotateY(v0);
        this.mCamera.translate(0f, 0f, ((float)(-this.halfWidth * 3)));
        this.mCamera.getMatrix(v1);
        this.mCamera.restore();
        v1.preTranslate(((float)(-this.halfWidth)), ((float)(-this.halfHeight)));
        v1.postTranslate(((float)this.halfWidth), ((float)this.halfHeight));
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
    }
}

