package com.lei.homework;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Activity extends android.app.Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Log.i("test","Activity: "+"onCreate");
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.lei.action.service");
                ServiceConnection conn = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };
                startService(intent);
//                bindService(intent,conn,BIND_AUTO_CREATE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("test", "Activity: " + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test", "Activity: " + "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("test", "Activity: " + "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("test", "Activity: " + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("test", "Activity: " + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("test", "Activity: " + "onDestroy");
    }
}
