package com.lei.lesson14_homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_homework01,btn_homework02,btn_homework03;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn_homework01 = (Button) findViewById(R.id.btn_main_homework01);
        btn_homework02 = (Button) findViewById(R.id.btn_main_homework02);
        btn_homework03 = (Button) findViewById(R.id.btn_main_homework03);
        intent = new Intent();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_homework01:
                intent.setAction("com.lei.lesson14_homework.action.homework01");
                startActivity(intent);
                break;
            case R.id.btn_main_homework02:
                intent.setAction("com.lei.lesson14_homework.action.homework02");
                startActivity(intent);
                break;
            case R.id.btn_main_homework03:
                intent.setAction("com.lei.lesson14_homework.action.homework03");
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.homework01,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
