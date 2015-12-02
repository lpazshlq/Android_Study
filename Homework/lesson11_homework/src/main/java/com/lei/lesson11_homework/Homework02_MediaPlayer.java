package com.lei.lesson11_homework;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lei.lesson11_model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Homework02_MediaPlayer extends Activity {
    ListView lv;
    SeekBar sbar_music;
    TextView tv_musicName, tv_musicTime;
    ImageButton ibtn_previouse, ibtn_start_pause, ibtn_next;
    ArrayList<Song> list_music;
    MyListener listener;
    MyAdapter adapter;
    MyHandler handler;
    int musicTime, music_listID;//当前播放时间  播放歌曲list位置
    boolean isPlay;//是否播放
    MediaPlayer mediaPlayer;
    String musicPath, musicTime_str;//MP3文件路径  播放时间String格式
    Timer timer;
    MyTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework02_mediaplayer);
        init();
        listener();
        {
            music_listID = 0;//初始化播放文件list位置
            musicPrepare();//初始化mediaPlayer准备文件等待播放
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlay) {
            mediaPlayer.start();
            taskStart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isPlay) {
            mediaPlayer.pause();
            taskStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            isPlay = false;
            musicStop();
            taskStart();
        }
    }

    private void init() {
        list_music = (ArrayList<Song>) getIntent().getExtras().getSerializable("music_list");
        lv = (ListView) findViewById(R.id.lv_homework02_music);
        sbar_music = (SeekBar) findViewById(R.id.sbr_homework02_music);
        tv_musicName = (TextView) findViewById(R.id.tv_homework02_music);
        tv_musicTime = (TextView) findViewById(R.id.tv_playtime_homework02_music);
        ibtn_previouse = (ImageButton) findViewById(R.id.ibtn_previous);
        ibtn_start_pause = (ImageButton) findViewById(R.id.ibtn_start_pause);
        ibtn_next = (ImageButton) findViewById(R.id.ibtn_next);
        listener = new MyListener();
        adapter = new MyAdapter();
        handler = new MyHandler();
        mediaPlayer = new MediaPlayer();
        lv.setAdapter(adapter);
    }

    private void listener() {
        lv.setOnItemClickListener(listener);
        sbar_music.setOnSeekBarChangeListener(listener);
        ibtn_previouse.setOnClickListener(listener);
        ibtn_start_pause.setOnClickListener(listener);
        ibtn_next.setOnClickListener(listener);
    }

    private String getHourMinuteSecond(int second) {
        int hour;
        int minute;
        if (second < 1) {
            return "00:00";
        } else if (second < 10) {
            return "00:0" + second;
        } else if (second < 60) {
            return "00:" + second;
        } else {
            minute = second / 60;
            second = second % 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                } else {
                    return "0" + minute + ":" + second;
                }
            } else if (minute < 60) {
                if (second < 10) {
                    return minute + ":0" + second;
                } else {
                    return minute + ":" + second;
                }
            } else {
                hour = minute / 60;
                minute = minute % 60;
                if (minute < 10) {
                    if (second < 10) {
                        return hour + ":0" + minute + ":0" + second;
                    } else {
                        return hour + ":0" + minute + ":" + second;
                    }
                } else {
                    if (second < 10) {
                        return hour + ":" + minute + ":0" + second;
                    } else {
                        return hour + ":" + minute + ":" + second;
                    }
                }
            }
        }
    }

    class MyListener implements View.OnClickListener, AdapterView.OnItemClickListener, MediaPlayer.OnSeekCompleteListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_previous:
                    if (music_listID > 0) {
                        music_listID--;
                        musicStop();
                        musicPrepare();
                        musicPlay();
                    } else {
                        Toast.makeText(Homework02_MediaPlayer.this,"已至播放列表顶部",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ibtn_start_pause:
                    if (isPlay) {
                        musicPause();
                    } else {
                        musicPlay();
                    }
                    break;
                case R.id.ibtn_next:
                    if (music_listID < list_music.size()-1) {
                        music_listID++;
                        musicStop();
                        musicPrepare();
                        musicPlay();
                    } else {
                        Toast.makeText(Homework02_MediaPlayer.this,"已至播放列表底部",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            music_listID = position;
            musicStop();
            musicPrepare();
            musicPlay();
        }

        @Override
        public void onSeekComplete(MediaPlayer mp) {
            musicStop();
            taskStop();
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            musicTime_str = getHourMinuteSecond(progress);
            musicTime = progress;
            handler.sendEmptyMessage(0x001);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            seekTo();
        }
    }

    /*导入音乐文件*/
    private void musicPrepare() {
        try {
            musicPath = list_music.get(music_listID).getUrl();//获得播放文件路径
            mediaPlayer.setDataSource(musicPath);
            mediaPlayer.prepare();
            sbar_music.setMax(mediaPlayer.getDuration() / 1000);//初始化sbar进度条最大值
            sbar_music.setProgress(0);//初始化sbar进度条至起始位置
            tv_musicName.setText(list_music.get(music_listID).getSname());//设置待播放音乐名称
            tv_musicTime.setText(getHourMinuteSecond(0)+"/"+getHourMinuteSecond(mediaPlayer.getDuration() / 1000));//设置待播放音乐时间String显示
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /*播放音乐*/
    private void musicPlay() {
        isPlay = true;
        ibtn_start_pause.setImageResource(android.R.drawable.ic_media_pause);
        mediaPlayer.start();
        taskStop();
        taskStart();
    }

    /*暂停音乐*/
    private void musicPause() {
        isPlay = false;
        ibtn_start_pause.setImageResource(android.R.drawable.ic_media_play);
        mediaPlayer.pause();
        taskStop();
    }

    /*停止音乐*/
    private void musicStop() {
        isPlay = false;
        mediaPlayer.stop();
        mediaPlayer.reset();
        taskStop();
    }

    /*指定播放时间*/
    private void seekTo() {
        mediaPlayer.seekTo(musicTime * 1000);
        sbar_music.setProgress(musicTime);
        handler.sendEmptyMessage(0x001);
        if (isPlay) {
            taskStop();
            taskStart();
        }
    }

    /*开始播放音乐开启同步进程*/
    private void taskStart() {
        timer = new Timer();
        task = new MyTask();
        timer.schedule(task, 0, 1000);
    }

    /*暂停或停止音乐关闭同步进程*/
    private void taskStop() {
        if (timer != null) {
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }
    }

    class MyTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(0x002);
            musicTime++;
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    tv_musicTime.setText(musicTime_str+"/"+getHourMinuteSecond(mediaPlayer.getDuration()/1000));
                    break;
                case 0x002:
                    tv_musicTime.setText(musicTime_str+"/"+getHourMinuteSecond(mediaPlayer.getDuration()/1000));
                    sbar_music.setProgress(musicTime);
                    break;
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list_music.size();
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
            convertView = getLayoutInflater().inflate(R.layout.homework02_mediaplayer_item, parent, false);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_homework02_mediaplayer_item);
            tv.setText(list_music.get(position).getSname());
            return convertView;
        }
    }
}
