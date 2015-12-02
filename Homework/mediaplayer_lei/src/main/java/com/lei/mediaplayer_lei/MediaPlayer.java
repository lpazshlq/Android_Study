package com.lei.mediaplayer_lei;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lei.model.Song;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MediaPlayer extends Activity implements View.OnClickListener {
    private EditText edt_search;
    private Button btn_search,btn_play,btn_down;
    private ListView lv;
    private ArrayList<Song> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment);
        init();
        listener();
    }

    private void init() {
        btn_down = (Button) findViewById(R.id.btn_down_search_fragment);
        btn_play = (Button) findViewById(R.id.btn_addlist_search_fragment);
        btn_search = (Button) findViewById(R.id.btn_search_search_fragment);
        lv = (ListView) findViewById(R.id.lv_search_fragment);
        edt_search = (EditText) findViewById(R.id.edt_search_fragment);
        list = new ArrayList<>();
    }

    private void listener() {

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
            convertView = getLayoutInflater().inflate(R.layout.search_fragment_item,parent,false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_search_fragment_item);
            TextView tv_songName = (TextView) convertView.findViewById(R.id.tv_song_name_search_item);
            TextView tv_singerName = (TextView) convertView.findViewById(R.id.tv_singer_name_search_item);
            Song song = list.get(position);
            imageView.setImageBitmap(getPicture(song.getAlbum_Pic_Small()));
            tv_songName.setText(song.getSong_Name());
            tv_singerName.setText(song.getSinger_Name());
            return convertView;
        }
    }

    private Bitmap getPicture(String pic_url){
        try {
            URL url = new URL(pic_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search_search_fragment:
                break;
            case R.id.btn_addlist_search_fragment:
                break;
            case R.id.btn_down_search_fragment:
                break;
        }
    }
}
