package com.lei.lesson13_homework;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homework02 extends Activity {
    Button btn_sports,btn_entertainment,btn_economic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02);
        init();
    }

    private void init() {
        btn_sports = (Button) findViewById(R.id.btn_sports_news_homework02);
        btn_entertainment = (Button) findViewById(R.id.btn_entertainment_news_homework02);
        btn_economic = (Button) findViewById(R.id.btn_economic_news_homework02);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_sports_news_homework02:
                break;
            case R.id.btn_entertainment_news_homework02:
                break;
            case R.id.btn_economic_news_homework02:
                break;
        }
    }
}
