package com.lei.lesson12_homework;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Homework03 extends Activity  {
    ImageView iv_first, iv_second;
    ProgressBar pbar;
    static final int IMAGE_FIRST = 1, IMAGE_SECOND = 2;
    int[] pics = {R.drawable.monday, R.drawable.tuesday, R.drawable.wednesday, R.drawable.thursday, R.drawable.friday, R.drawable.saturdayandsunday};
    int selected = 1, selectedImage = 2;
    Timer timer;
    MyTask task;
    Animation anim_in, anim_out;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework03);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer == null) {
            timer = new Timer();
            task = new MyTask();
            timer.schedule(task, 0, 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }
    }

    private void init() {
        pbar = (ProgressBar) findViewById(R.id.pBar_homework03);
        iv_first = (ImageView) findViewById(R.id.iv_first_homework03);
        iv_second = (ImageView) findViewById(R.id.iv_second_homework03);
        handler = new MyHandler();
        iv_first.setImageResource(pics[0]);
        anim_in = AnimationUtils.loadAnimation(this, R.anim.set_in_homework03);
        anim_out = AnimationUtils.loadAnimation(this, R.anim.set_out_homework03);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x001:
                    iv_first.setVisibility(View.VISIBLE);
                    iv_first.setImageResource(pics[selected%6]);
                    iv_first.startAnimation(anim_in);
                    iv_second.startAnimation(anim_out);
                    iv_second.setVisibility(View.INVISIBLE);
                    break;
                case 0x002:
                    iv_second.setVisibility(View.VISIBLE);
                    iv_second.setImageResource(pics[selected % 6]);
                    iv_first.startAnimation(anim_out);
                    iv_second.startAnimation(anim_in);
                    iv_first.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }
    int m = 0;
    int n = 0;
    class MyListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    m = (int) event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    n = (int) event.getX();
                    int dx = n-m;
                    if (dx>0){

                    }
                    break;
            }
            return false;
        }
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            switch (selectedImage) {
                case IMAGE_FIRST:
                    selectedImage = IMAGE_SECOND;
                    selected++;
                    pbar.setProgress(selected%6+1);
                    handler.sendEmptyMessage(0x001);
                    break;
                case IMAGE_SECOND:
                    selectedImage = IMAGE_FIRST;
                    selected++;
                    pbar.setProgress(selected%6+1);
                    handler.sendEmptyMessage(0x002);
                    break;
            }
        }
    }
}
