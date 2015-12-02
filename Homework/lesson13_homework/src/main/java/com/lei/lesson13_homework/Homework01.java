package com.lei.lesson13_homework;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class Homework01 extends Activity {
    TabHost tabHost;
    TabHost.TabSpec tabSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework01);
        init();
        listener();
    }

    private void init() {
        tabHost = (TabHost) findViewById(R.id.tabhost_homework01);
        tabHost.setup();
        tabSpec = tabHost.newTabSpec("fm01").setIndicator("FM01").setContent(R.id.tab1_homework01);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("fm02").setIndicator("FM02").setContent(R.id.tab2_homework01);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("fm03").setIndicator("FM03").setContent(R.id.tab3_homework01);
        tabHost.addTab(tabSpec);
    }

    private void listener() {

    }
}
