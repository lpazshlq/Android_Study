package com.lei.lesson10_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lei.lesson10_homework.R;

import java.util.Timer;
import java.util.TimerTask;

public class Homework03_View extends SurfaceView implements SurfaceHolder.Callback {
    private Paint paint = new Paint();
    private Bitmap bg;
    private Bitmap wind;
    private Bitmap point;
    private float rotate = 0;
    private Timer timer;
    private TimerTask task;
    private float percentX = 1.0f;
    private float percentY = 1.0f;

    public Homework03_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.RGBA_8888);
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_na);
        wind = BitmapFactory.decodeResource(getResources(), R.drawable.na_windmill);
        point = BitmapFactory.decodeResource(getResources(), R.drawable.na_point);

    }

    public void draw(){
        Canvas canvas = getHolder().lockCanvas();
        if (canvas!=null){
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(bg, null, rectF, paint);
            Matrix matrix = new Matrix();
            matrix.postRotate((rotate += 2) % 360f, wind.getWidth() / 2, wind.getHeight() / 2);
            int dx = 250/(bg.getWidth()/getWidth());
            int dy = 500/(bg.getHeight()/getHeight());
            matrix.postTranslate(0,dy-wind.getHeight()/2);
            canvas.drawBitmap(wind, matrix, paint);
            canvas.drawBitmap(point,dx-point.getWidth()/2,dy-point.getHeight()/2,paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        percentX = bg.getWidth()/getWidth();
        percentY = bg.getHeight()/getHeight();
        wind = Bitmap.createScaledBitmap(wind, (int) (wind.getWidth()/percentX), (int) (wind.getHeight()/percentY),true);
        point = Bitmap.createScaledBitmap(point,(int)(point.getWidth()/percentX),(int)(point.getHeight()/percentY),true);
        Log.i("test",getWidth()+"=123");
        Log.i("test",getHeight()+"=456");
        if (timer==null){
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    draw();
                }
            };
            timer.schedule(task,0,3);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (timer!=null){
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }
    }
}
