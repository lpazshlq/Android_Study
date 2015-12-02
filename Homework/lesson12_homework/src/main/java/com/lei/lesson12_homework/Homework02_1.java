package com.lei.lesson12_homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Homework02_1 extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02_1);
        TextView tv = (TextView) findViewById(R.id.tv_homework02_1);
        tv.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.set_in2_homework02,R.anim.set_out2_homework02);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("com.lei.action.lesson12_homework02_2");
        startActivity(intent);
        overridePendingTransition(R.anim.set_in2_homework02,R.anim.set_out2_homework02);
    }
}
