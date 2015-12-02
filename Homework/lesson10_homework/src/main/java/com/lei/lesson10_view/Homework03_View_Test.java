package com.lei.lesson10_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lei.lesson10_homework.R;

public class Homework03_View_Test extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder holder;
    private boolean isRunning = true;

    private int screenWidth;
    private int screenHeight;
    private Bitmap windPoint;
    private Bitmap windmill;
    private Bitmap viewnBg;


    public Homework03_View_Test(Context context) {
        super(context);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.RGBA_8888);
        getViewSize(context);
        LoadWindmillImage();
    }

    private void LoadWindmillImage() {
        viewnBg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_na);
        windmill = BitmapFactory.decodeResource(getResources(),R.drawable.na_windmill);
        windPoint = BitmapFactory.decodeResource(getResources(), R.drawable.na_point);
        float percent = percentumW();
        int _witdh = (int)(250/percent);
        windmill = Bitmap.createScaledBitmap(windmill,_witdh*2,_witdh*2,true);
    }

    private void getViewSize(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
        Log.i("test",screenHeight+"|"+screenWidth);
    }

    private float percentumW(){
        float bg_width = viewnBg.getWidth();
        return bg_width/screenWidth;
    }

    private float percentumH(){
        float bg_height = viewnBg.getHeight();
        return bg_height/screenHeight;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        float rotate = 0;

        while (isRunning){
            Canvas canvas = null;
            synchronized (this){
                try {
                    canvas = holder.lockCanvas();
                    if (canvas!=null){
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);

                        paint.setFilterBitmap(true);
                        RectF rectF = new RectF(0,0,screenWidth,screenHeight);
                        canvas.drawBitmap(viewnBg, null, rectF, paint);
                        Matrix matrix = new Matrix();
                        matrix.postRotate((rotate += 2) % 360f, windmill.getWidth() / 2, windmill.getHeight() / 2);

                        int _dy = (int)(500/percentumH());
                        matrix.postTranslate(0, (_dy - (windmill.getHeight() / 2)));
                        canvas.drawBitmap(windmill,matrix,paint);

                        int _dx = (int)(250/percentumW());
                        canvas.drawBitmap(windPoint,_dx - windPoint.getWidth()/2,_dy - windPoint.getHeight()/2,paint);
                        Thread.sleep(3);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    if (canvas!=null){
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
