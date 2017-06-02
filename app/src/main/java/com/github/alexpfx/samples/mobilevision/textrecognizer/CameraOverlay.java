package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.gms.vision.CameraSource;

import java.util.Objects;

/**
 * Created by alexandre on 01/06/2017.
 */

public class CameraOverlay extends View {
    private int mWidth;
    private int mHeight;
    private int mFacing;

    private Paint mPaintRecognized = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rect = new RectF(100, 45, 72, 10);
    private CameraSource mCameraSource;
    private float mWidthScaleFactor = 1;
    private float mHeightScaleFactor = 1;

    public CameraOverlay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaintRecognized.setStrokeWidth(3f);
        mPaintRecognized.setStyle(Paint.Style.STROKE);
        mPaintRecognized.setColor(Color.RED);
    }


    public void setCameraSource(CameraSource cameraSource) {
        mCameraSource = cameraSource;
        int width = cameraSource.getPreviewSize().getWidth();
        int height = cameraSource.getPreviewSize().getHeight();
        setCameraInfo(width, height, cameraSource.getCameraFacing());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private float translateX(float x) {
        Objects.requireNonNull(mCameraSource, "camera source cannot be null");
        if (mCameraSource.getCameraFacing() == CameraSource.CAMERA_FACING_FRONT) {
            return mCameraSource.getPreviewSize().getWidth() - scaleX(x);
        } else {
            return scaleX(x);
        }
    }

    private float translateY(float y) {
        return scaleY(y);
    }

    private float scaleY(float y) {
        return y * mHeightScaleFactor;
    }

    private float scaleX(float x) {
        return x * mWidthScaleFactor;
    }

    public void setCameraInfo(int width, int height, int facing) {
        mWidth = width;
        mHeight = height;
        mFacing = facing;
    }

    public void setPosition(Rect newPosition) {
        rect.set(translateX(newPosition.left), translateY(newPosition.top), translateX(newPosition.right), translateY(newPosition.bottom));
        postInvalidate();
        clear();

    }

    Handler mHandler = new Handler(Looper.getMainLooper());


    public void clear() {
        mHandler.postDelayed(() -> {
            rect = new RectF();
            postInvalidate();
        }, 750);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if (mWidth != 0 && mHeight != 0) {
            mWidthScaleFactor = (float) canvas.getWidth() / (float) mWidth;
            mHeightScaleFactor = (float) canvas.getHeight() / (float) mHeight;
        }

        canvas.drawRect(rect, mPaintRecognized);
    }
}

