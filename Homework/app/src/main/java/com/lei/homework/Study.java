package com.lei.homework;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;

public class Study extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("test","Service: "+"onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("test", "Service: "+"onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("test", "Service: "+"onStart");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("test", "Service: "+"onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("test","Service: "+"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("test","Service: "+"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test", "Service: "+"onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("test", "Service: "+"onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("test", "Service: "+"onLowMemory");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i("test", "Service: "+"onTaskRemoved");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i("test", "Service: "+"onTrimMemory");
    }
}
