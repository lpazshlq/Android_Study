package com.lei.lesson13_homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_homework01,btn_homework02,btn_homework03;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn_homework01 = (Button) findViewById(R.id.btn_homework01);
        btn_homework02 = (Button) findViewById(R.id.btn_homework02);
        btn_homework03 = (Button) findViewById(R.id.btn_homework03);
        intent = new Intent();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_homework01:
                intent.setAction("com.lei.action.lesson13_homework01");
                startActivity(intent);
                break;
            case R.id.btn_homework02:
                intent.setAction("com.lei.action.lesson13_homework02");
                startActivity(intent);
                break;
            case R.id.btn_homework03:
                intent.setAction("com.lei.action.lesson13_homework03");
                startActivity(intent);
                break;
        }
    }
}
