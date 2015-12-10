package com.lei.android.android_utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lei.android.adapter.PullRefreshAdapter;
import com.lei.android.pullrefresh_listview.PullRefreshListView;
import com.lei.android.widget.MyPullRefreshListView;

import java.util.ArrayList;

public class ListViewPullRefresh extends AppCompatActivity {
    private MyPullRefreshListView lv_pullfresh;

    private PullRefreshAdapter adapter;

    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_pullrefresh);
        init();
        listener();
        getData();
    }

    private void init() {
        lv_pullfresh = (MyPullRefreshListView) findViewById(R.id.lv_pullrefresh);
        adapter = new PullRefreshAdapter(getApplicationContext());
        lv_pullfresh.setAdapter(adapter);
    }

    private void listener() {
    }

    private void getData() {
        String strs[] = getResources().getStringArray(R.array.test);
        for (String str : strs){
            items.add(str);
        }
        adapter.setItems(items);
    }
}
