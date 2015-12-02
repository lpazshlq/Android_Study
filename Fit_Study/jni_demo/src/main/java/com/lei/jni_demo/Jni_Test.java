package com.lei.jni_demo;

public class Jni_Test {
    static {
        System.loadLibrary("Jni_Test");
    }

    public static native double getData(char[] ch,int rate,int soundChannel);

    public static native int getInt();
}
