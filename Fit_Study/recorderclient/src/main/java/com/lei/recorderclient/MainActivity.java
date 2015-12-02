package com.lei.recorderclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_START_ID = Menu.FIRST;

    public static final int MENU_STOP_ID = Menu.FIRST + 1;

    public static final int MENU_EXIT_ID = Menu.FIRST + 2;

    protected MyAudioTrack m_player;

    protected MyAudioRecord m_recorder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu aMenu) {
        boolean res = super.onCreateOptionsMenu(aMenu);
        aMenu.add(0, MENU_START_ID, 0, "START");
        aMenu.add(0, MENU_STOP_ID, 0, "STOP");
        aMenu.add(0, MENU_EXIT_ID, 0, "EXIT");
        return res;
    }

    public boolean onOptionsItemSelected(MenuItem aMenuItem) {
        switch (aMenuItem.getItemId()) {
            case MENU_START_ID:
                m_player = new MyAudioTrack();
                m_recorder = new MyAudioRecord();
//                m_player.init();
                new Thread(){
                    @Override
                    public void run() {
                        m_recorder.init();
                        m_recorder.start();
                    }
                }.start();
//                m_player.start();
                break;
            case MENU_STOP_ID:
                m_recorder.free();
                m_player.free();
                m_player = null;
                m_recorder = null;
                break;
            case MENU_EXIT_ID:
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                break;
        }
        return super.onOptionsItemSelected(aMenuItem);
    }
}
