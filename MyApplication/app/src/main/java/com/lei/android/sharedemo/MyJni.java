package com.lei.android.sharedemo;

public class MyJni {
    static {
        System.loadLibrary("MyJni");
    }

    public static native int getInt();
}
