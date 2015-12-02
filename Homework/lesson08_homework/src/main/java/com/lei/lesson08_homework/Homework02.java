package com.lei.lesson08_homework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Homework02 extends Activity implements RadioGroup.OnCheckedChangeListener {
    private TextView tv;
    private RadioGroup rg_size, rg_color;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02);
        init();
        listener();
        fill();
    }

    private void fill() {
        int size = sp.getInt("size", 0);
        int color = sp.getInt("color", 0);
        if (size != 0) {
            tv.setTextSize(size);
        }
        if (color != 0) {
            tv.setTextColor(color);
        }
    }

    private void init() {
        tv = (TextView) findViewById(R.id.tv_homework02);
        rg_size = (RadioGroup) findViewById(R.id.rg1_homework02);
        rg_color = (RadioGroup) findViewById(R.id.rg2_homework02);
        sp = getSharedPreferences("sp_homework02", 0);
        editor = sp.edit();
    }

    private void listener() {
        rg_size.setOnCheckedChangeListener(this);
        rg_color.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()){
            case R.id.rg1_homework02:
                switch (checkedId) {
                    case R.id.rbtn_15sp_homework02:
                        editor.putInt("size", 15);
                        tv.setTextSize(15);
                        break;
                    case R.id.rbtn_30sp_homework02:
                        editor.putInt("size", 30);
                        tv.setTextSize(30);
                        break;
                    case R.id.rbtn_45sp_homework02:
                        editor.putInt("size", 45);
                        tv.setTextSize(45);
                        break;
                }
                break;
            case R.id.rg2_homework02:
                switch (checkedId){
                    case R.id.rbtn_red_homework02:
                        int red = Color.RED;
                        editor.putInt("color", red);
                        tv.setTextColor(red);
                        break;
                    case R.id.rbtn_green_homework02:
                        int green = Color.GREEN;
                        editor.putInt("color", green);
                        tv.setTextColor(green);
                        break;
                    case R.id.rbtn_blue_homework02:
                        int blue = Color.BLUE;
                        editor.putInt("color", blue);
                        tv.setTextColor(blue);
                        break;
                }
                break;
        }
        editor.commit();
    }
}
