package com.lei.mediaplayer_lei;

        import android.content.Intent;
        import android.support.v4.view.PagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int[] pics = {R.drawable.pic_main_01,R.drawable.pic_main_02,R.drawable.pic_main_03};
    ArrayList<ImageView> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpager_main);
        for (int i = 0;i<pics.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(pics[i]);
            list.add(imageView);
        }
        viewPager.setAdapter(new MyViewPagerAdapter());
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = list.get(position);
            if (position == 2){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.lei.action.mediaplayer_view");
                        startActivity(intent);
                        finish();
                    }
                });
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }
}
