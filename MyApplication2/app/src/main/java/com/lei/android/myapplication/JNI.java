package com.lei.android.myapplication;

public class JNI {
    static {
        System.loadLibrary("Test");
    }

    public static native void init_Featureget();

    public static native void inputPCM(byte[] pcm, int datasize, int rate, int channelnum);

    public static native byte[] getPcmResult();
}
