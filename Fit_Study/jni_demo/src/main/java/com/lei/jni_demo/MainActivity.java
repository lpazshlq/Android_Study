package com.lei.jni_demo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Button btn_jni;
    private TextView tv_jni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    private void init() {
        btn_jni = (Button) findViewById(R.id.btn_jni);
        tv_jni = (TextView) findViewById(R.id.tv_jni);

    }

    private void listener() {
        btn_jni.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_jni:
                char[] ch = {'a','b'};
                double x = Jni_Test.getData(ch,2,2);
                tv_jni.setText(x+"");
                break;
        }
    }
}
