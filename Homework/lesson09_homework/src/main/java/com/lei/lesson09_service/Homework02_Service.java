package com.lei.lesson09_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Homework02_Service extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
