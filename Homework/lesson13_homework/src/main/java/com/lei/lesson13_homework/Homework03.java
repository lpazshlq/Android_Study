package com.lei.lesson13_homework;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class Homework03 extends Activity {
    ViewPager viewPager;
    int index;
    ArrayList<ImageView> list;
    int[] images = {
            R.drawable.img00000,
            R.drawable.img00001,
            R.drawable.img00002,
            R.drawable.img00003,
            R.drawable.img00004,
            R.drawable.img00005,
            R.drawable.img00006,
            R.drawable.img00007,
            R.drawable.img00008,
            R.drawable.img00009,
            R.drawable.img00010,
            R.drawable.img00011,
            R.drawable.img00012,
            R.drawable.img00013,
            R.drawable.img00014
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework03);
        init();
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.vpager_homework03);
        list = new ArrayList<>();
        for (int i:images){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(i);
            list.add(imageView);
        }
        index = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2%images.length);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(index);
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            while(true){
                if (isCancelled()){
                    break;
                }
                try {
                    Thread.sleep(500);
                    index++;
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        /**
         * 显示总页数
         * @return
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = list.get(position%images.length);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position%images.length));
        }
    }
}
