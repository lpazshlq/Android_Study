package com.lei.jni_demo;

import android.os.Parcel;
import android.os.Parcelable;

public class Jni_Demo implements Parcelable {
    String name;
    int age;
    String gender;
    String address;

//    /**
//     * static()表示在loading time (载入类的时候) 的时候执行下面的代码
//     */
//    static {
//        System.loadLibrary("Jni_Demo");
//    }
//
//    /**
//     * 本地函数接口(java通过它调用C/C++)
//     */
//    public static native String sayHello();
//
//    public static native int add(int x,int y);
//
//    public static native String sayGoodBye(String str);
//
//    public static native String doWhat(String name,int age);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.gender);
        dest.writeString(this.address);
    }

    public Jni_Demo() {
    }

    protected Jni_Demo(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.gender = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<Jni_Demo> CREATOR = new Parcelable.Creator<Jni_Demo>() {
        public Jni_Demo createFromParcel(Parcel source) {
            return new Jni_Demo(source);
        }

        public Jni_Demo[] newArray(int size) {
            return new Jni_Demo[size];
        }
    };
}
