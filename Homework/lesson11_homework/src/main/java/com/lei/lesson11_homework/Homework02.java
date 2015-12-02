package com.lei.lesson11_homework;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.lei.lesson11_model.Song;

import java.util.ArrayList;

public class Homework02 extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private ArrayList<Song> list;
    private ContentResolver resolver;
    private Button btn_addlist,btn_refresh;
    private CheckBox cbox_homework02;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02);
        init();
        getMusic();
        listener();
    }

    private void getMusic() {
        list.clear();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String sname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            list.add(new Song(duration,sname,url));
        }
    }

    private void init() {
        ListView lv = (ListView) findViewById(R.id.lv_homework02);
        list = new ArrayList<>();
        resolver = getContentResolver();
        btn_addlist = (Button) findViewById(R.id.btn_addlist_homework02);
        btn_addlist.setEnabled(false);
        btn_refresh = (Button) findViewById(R.id.btn_refresh_homework02);
        cbox_homework02 = (CheckBox) findViewById(R.id.cbox_homework02);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }

    private void listener() {
        cbox_homework02.setOnCheckedChangeListener(this);
        btn_addlist.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addlist_homework02:
                Intent intent = new Intent("com.lei.action.lesson11_homework02_mediaplayer");
                Bundle bundle = new Bundle();
                ArrayList<Song> playlist;
                playlist = new ArrayList<>();
                for (int i =0;i<list.size();i++){
                    if (list.get(i).isSelected()){
                        playlist.add(list.get(i));
                    }
                }
                bundle.putSerializable("music_list",playlist);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_refresh_homework02:
                getMusic();
                for (Song song:list){
                    song.setIsSelected(false);
                }
                btn_addlist.setEnabled(false);
                cbox_homework02.setChecked(false);
                cbox_homework02.setText("全选");
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cbox_homework02:
            if (isChecked){
                for (Song song:list){
                    song.setIsSelected(true);
                }
                btn_addlist.setEnabled(true);
                cbox_homework02.setText("全不选");
                adapter.notifyDataSetChanged();
            }else {
                for (Song song:list){
                    song.setIsSelected(false);
                }
                btn_addlist.setEnabled(false);
                cbox_homework02.setText("全选");
                adapter.notifyDataSetChanged();
            }
                break;
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
            convertView = getLayoutInflater().inflate(R.layout.homework02_item1, parent, false);
            final CheckBox cbox = (CheckBox) convertView.findViewById(R.id.cbox_homework02_item1);
            final int temp = position;
            cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        list.get(temp).setIsSelected(true);
                        buttonView.setChecked(true);
                        btn_addlist.setEnabled(true);
                    } else {
                        list.get(temp).setIsSelected(false);
                        buttonView.setChecked(false);
                        boolean btnEnable = false;
                        for (Song song : list) {
                            if (song.isSelected()) {
                                btnEnable = true;
                                break;
                            }
                        }
                        btn_addlist.setEnabled(btnEnable);
                    }
                }
            });
            cbox.setText(list.get(position).getSname()+"");
            if (list.get(position).isSelected()){
                cbox.setChecked(true);
            }else{
                cbox.setChecked(false);
            }
            return convertView;
        }
    }
}
