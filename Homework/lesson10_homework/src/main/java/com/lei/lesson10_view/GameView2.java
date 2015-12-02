package com.lei.lesson10_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lei.lesson10_homework.R;

public class GameView2 extends SurfaceView {
    SurfaceHolder mholder;
    Bitmap bmpBackground = null;

    public GameView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        bmpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.na_windmill);
        mholder = getHolder();
        mholder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                draw();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void draw() {
        Canvas canvas = mholder.lockCanvas();
        if (canvas==null){
            return;
        }
        Log.i("test","draw");
        drawBackground(canvas);
        mholder.unlockCanvasAndPost(canvas);
    }

    private void drawBackground(Canvas canvas){
        canvas.drawBitmap(bmpBackground,0,0,null);
    }
}
