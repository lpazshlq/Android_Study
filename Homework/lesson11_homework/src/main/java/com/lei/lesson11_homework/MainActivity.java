package com.lei.lesson11_homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_homework01,btn_homework02,btn_homework03;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    private void init() {
        btn_homework01 = (Button) findViewById(R.id.btn_homework01);
        btn_homework02 = (Button) findViewById(R.id.btn_homework02);
        btn_homework03 = (Button) findViewById(R.id.btn_homework03);
        intent = new Intent();
    }

    private void listener() {
        btn_homework01.setOnClickListener(this);
        btn_homework02.setOnClickListener(this);
        btn_homework03.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_homework01:
                intent.setAction("com.lei.action.lesson11_homework01");
                startActivity(intent);
                break;
            case R.id.btn_homework02:
                intent.setAction("com.lei.action.lesson11_homework02");
                startActivity(intent);
                break;
            case R.id.btn_homework03:
                intent.setAction("com.lei.action.lesson11_homework03");
                startActivity(intent);
                break;
        }
    }
}
