package com.lei.lesson10_homework;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Homework02 extends Activity implements View.OnClickListener,View.OnTouchListener {
    private SurfaceView sfv;
    private SurfaceHolder holder;
    private Timer timer;
    private TimerTask task;
    private int x = 20;
    private int y = 20;
    private boolean xflag,yflag;
    private Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02);
        init();
        listener();
    }

    private void init() {
        sfv = (SurfaceView) findViewById(R.id.sfv_homework02);
        holder = sfv.getHolder();
        holder.addCallback(new MyCallBack());
    }

    private void listener() {
        sfv.setOnClickListener(this);
        sfv.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sfv_homework02:
                if (timer==null){
                    timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            draw(x,y);
                            xyflag();

                        }
                    };
                    timer.schedule(task,0,50);
                }else{
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                break;
        }
    }

    private void draw(float x,float y){
        Canvas canvas = holder.lockCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(x,y,20,paint);
        holder.unlockCanvasAndPost(canvas);
    }

    private void xyflag() {
        if (!xflag){
            x+=(r.nextInt(30)+5);
            if (x>sfv.getWidth()-20){
                xflag=true;
            }
        }else{
            x-=(r.nextInt(30)+5);
            if (x<20){
                xflag=false;
            }
        }
        if (!yflag){
            y+=(r.nextInt(30)+5);
            if (y>sfv.getHeight()-20){
                yflag = true;
            }
        }else{
            y-=(r.nextInt(30)+5);
            if (y<20){
                yflag = false;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) event.getX();
                y = (int) event.getY();
                break;
        }
        draw(x,y);
        return true;
    }

    class MyCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            holder.unlockCanvasAndPost(canvas);
            if (timer==null){
                timer = new Timer();
                task = new TimerTask() {
                    @Override
                    public void run() {
                        draw(x,y);
                        xyflag();

                    }
                };
                timer.schedule(task,0,50);
            }else{
                timer.cancel();
                task.cancel();
                timer = null;
                task = null;
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

    @Override
    protected void onPause() {
        super.onPause();
        if (timer!=null){
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }
    }
}
