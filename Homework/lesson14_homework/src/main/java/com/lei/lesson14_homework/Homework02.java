package com.lei.lesson14_homework;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class Homework02 extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02);
        init();
    }

    private void init() {
        lv = (ListView) findViewById(R.id.lv_homework02);
        lv.setAdapter(new MyAdapter());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("10086");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homework01,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){

        }
        return super.onContextItemSelected(item);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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
            registerForContextMenu(convertView);
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i("test","LongClick");
                    Dialog dialog = new Dialog(Homework02.this);
                    dialog.setTitle("title");
                    dialog.show();
                    return true;
                }
            });
            return convertView;
        }
    }
}
