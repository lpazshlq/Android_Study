package com.lei.lesson09_homework_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyProvider extends ContentProvider {
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "student";

    @Override
    public boolean onCreate() {
        MyOpenHelper openHelper = new MyOpenHelper(getContext(), "data_provider.db", null, 1);
        database = openHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return database.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        database.insert(TABLE_NAME,null,values);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(TABLE_NAME,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(TABLE_NAME,values,selection,selectionArgs);
    }

    class MyOpenHelper extends SQLiteOpenHelper {

        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String CREATE_TABLE_CMD = "create table student (" +
                    "id integer primary key autoincrement, name string," +
                    "age integer, score integer );";
            db.execSQL(CREATE_TABLE_CMD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
