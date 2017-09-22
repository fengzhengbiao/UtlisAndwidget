package com.jokerfishlib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jokerfishlib.R;

/**
 * Created by JokerFish on 2017/9/21.
 */

public class WaveView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private boolean isRunning;

    private Paint mPaint;
    private int mWaveCount, mWaveLength, mWaveHeight, mWidth, mHight;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = getHolder();
        mHolder.addCallback(this);
        //让背景变成透明
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.i("WaveView", "surfaceChanged: format:" + format + "  width: " + width + "  height: " + height);
        mHight = height;
        mWidth = width;
        mWaveHeight = mHight / 2;
        mWaveLength = mWidth / 2;
        mWaveCount = (int) Math.round(mWidth / mWaveLength + 1.5);
        mWaveHeight = mHight / 2;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            draw();
        }
    }

    private int mOffset = 0;

    private void draw() {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (canvas != null) {
            try {
                //使用获得的Canvas做具体的绘制
                Path mPath = new Path();
                mPath.moveTo(-mWaveLength + mOffset, mHight);
                for (int i = 0; i < mWaveCount; i++) {
                    mPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset, mHight * 2 - 10, (-mWaveLength / 2) + (i * mWaveLength) + mOffset, mHight);
                    mPath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mOffset, 10, i * mWaveLength + mOffset, mHight);
                }
                mPath.lineTo(mWidth + 200, mHight + 20);
                mPath.lineTo(-200, mHight);
                mOffset += 3;
                if (mOffset>mWidth/2){
                    mOffset=0;
                }
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                Thread.sleep(60);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

}
