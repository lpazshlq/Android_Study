package com.lei.recordserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MyServer myServer = new MyServer();
    MyThread myThread = new MyThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myThread.start();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                myServer.startServer();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
