package com.figure.exercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.figure.custom_widget.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2016/2/5.
 */
public class HorizontalListViewActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private HorizontalListView lvHorizontal;

    private HorizontalBaseAdapter adapter;

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_listview);
        lvHorizontal = (HorizontalListView) findViewById(R.id.lv_horizontal);
        adapter = new HorizontalBaseAdapter();
        for (int i = 0; i < 20; i++) {
            list.add("" + i);
        }
        lvHorizontal.setAdapter(adapter);
        adapter.setList(list);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    private class HorizontalBaseAdapter extends BaseAdapter {
        List<String> list = new ArrayList<>();

        public void setList(List<String> list) {
            this.list.clear();
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.horizontal_listview_item, parent, false);
            return convertView;
        }
    }

}
