package com.lei.audiorecorder_demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JniActivity extends ActionBarActivity implements View.OnClickListener {
    private Button btn_jni;
    private TextView tv_jni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        init();
        listener();
    }

    private void init() {
        btn_jni = (Button) findViewById(R.id.btn_jni_test);
        tv_jni = (TextView) findViewById(R.id.tv_jni_test);
    }

    private void listener() {
        btn_jni.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_jni_test:
                break;
        }
    }
}
