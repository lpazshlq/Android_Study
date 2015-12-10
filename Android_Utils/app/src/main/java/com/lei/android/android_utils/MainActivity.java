package com.lei.android.android_utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_lv_pullrefresh;

    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    private void init() {
        btn_lv_pullrefresh = (Button) findViewById(R.id.btn_lv_pullrefresh);
    }

    private void listener() {
        btn_lv_pullrefresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lv_pullrefresh:
                intent.setAction("com.lei.android.ListViewPullRefresh");
                startActivity(intent);
                break;
        }
    }
}
