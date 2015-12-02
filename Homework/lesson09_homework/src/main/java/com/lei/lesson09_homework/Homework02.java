package com.lei.lesson09_homework;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Homework02 extends Activity implements View.OnClickListener {
    ContentResolver resolver;
    Button btn_query;
    ListView lv;
    ArrayList<String> list = new ArrayList<>();
    MyAdapter adapter;
    MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02);
        init();
        listener();
    }

    private void init() {
        btn_query = (Button) findViewById(R.id.btn_query_homework02);
        lv = (ListView) findViewById(R.id.lv_homework02);
        adapter = new MyAdapter();
        handler = new MyHandler();
        lv.setAdapter(adapter);
    }

    private void listener() {
        btn_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(){
            @Override
            public void run() {
                list.clear();
                resolver = getContentResolver();
                Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    list.add(name);
                }
                handler.sendEmptyMessage(0x001);
            }
        }.start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x001:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.homework02_item,parent,false);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name_homework02_item);
            tv_name.setText(list.get(position));
            return convertView;
        }
    }
}
