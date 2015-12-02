package com.lei.audiorecorder_demo;

public class Jni {
    static{
        System.loadLibrary("JniTest");
    }

    public static native void setInt();
}
