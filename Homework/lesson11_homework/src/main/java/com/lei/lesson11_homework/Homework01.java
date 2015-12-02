package com.lei.lesson11_homework;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Homework01 extends Activity implements View.OnClickListener {
    private Button btn_start,btn_stop;
    private ImageView iv;
    private int[] images = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h};
    private int temp = 0;
    private MyTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework01);
        init();
        listener();
    }

    private void init() {
        btn_start = (Button) findViewById(R.id.btn_start_homework01);
        btn_stop = (Button) findViewById(R.id.btn_stop_homework01);
        iv = (ImageView) findViewById(R.id.iv_homework01);
    }

    private void listener() {
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_homework01:
                if (task==null){
                    task = new MyTask();
                    task.execute();
                }
                break;
            case R.id.btn_stop_homework01:
                if (task!=null){
                    task.cancel(true);
                    task = null;
                }
                break;
        }
    }

    class MyTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0;i<100;i++){
                if (isCancelled()){
                    break;
                }
                publishProgress();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            iv.setImageResource(images[temp++%8]);
            super.onProgressUpdate(values);
        }
    }
}
