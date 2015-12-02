package com.lei.lesson12_homework;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Homework01 extends Activity {
    final String PIC_URL = "http://www.xxjxsj.cn/article/UploadPic/2009-10/2009101018545196251.jpg";
    ImageView iv;
    Button btn_change,btn_resume;
    MyListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework01);
        init();
        listener();
    }

    private void init() {
        iv = (ImageView) findViewById(R.id.iv_homework01);
        btn_change = (Button)findViewById(R.id.btn_change_homework01);
        btn_resume = (Button) findViewById(R.id.btn_resume_homework01);
        new MyAsyncTask().execute();
        listener = new MyListener();
    }

    private void listener() {
        btn_change.setOnClickListener(listener);
        btn_resume.setOnClickListener(listener);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_change_homework01:
                    ObjectAnimator objAnimatorRotation_change = ObjectAnimator.ofFloat(iv,View.ROTATION_X,360f);
                    ObjectAnimator objAnimatorScaleX_change = ObjectAnimator.ofFloat(iv,View.SCALE_X,0f);
                    ObjectAnimator objAnimatorScaleY_change = ObjectAnimator.ofFloat(iv,View.SCALE_Y,0f);
                    ObjectAnimator objAnimatorAlpha_change = ObjectAnimator.ofFloat(iv,View.ALPHA,0f);
                    AnimatorSet animatorSet_change = new AnimatorSet();
                    animatorSet_change.playTogether(objAnimatorRotation_change,objAnimatorAlpha_change,objAnimatorScaleX_change,objAnimatorScaleY_change);
                    animatorSet_change.setDuration(3000).start();
                    break;
                case R.id.btn_resume_homework01:
                    ObjectAnimator objAnimatorRotation_resume = ObjectAnimator.ofFloat(iv,View.ROTATION_X,0f);
                    ObjectAnimator objAnimatorScaleX_resume = ObjectAnimator.ofFloat(iv,View.SCALE_X,1f);
                    ObjectAnimator objAnimatorScaleY_resume = ObjectAnimator.ofFloat(iv,View.SCALE_Y,1f);
                    ObjectAnimator objAnimatorAlpha_resume = ObjectAnimator.ofFloat(iv,View.ALPHA,1f);
                    AnimatorSet animatorSet_resume = new AnimatorSet();
                    animatorSet_resume.playTogether(objAnimatorRotation_resume,objAnimatorAlpha_resume,objAnimatorScaleX_resume,objAnimatorScaleY_resume);
                    animatorSet_resume.setDuration(3000).start();
                    break;
            }
        }
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            return getPicture(PIC_URL);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv.setImageBitmap(bitmap);
        }
    }

    private Bitmap getPicture(String pic_url){
        try {
            URL url = new URL(pic_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
