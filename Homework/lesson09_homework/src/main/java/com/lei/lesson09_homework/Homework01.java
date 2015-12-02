package com.lei.lesson09_homework;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lei.lesson09_model.Student;

import java.util.ArrayList;

public class Homework01 extends Activity implements View.OnClickListener {
    private Button btn_insert,btn_delete;
    private ListView lv;
    private EditText edt_name,edt_age,edt_score;
    private int selectItem = -1;
    private ArrayList<Student> list;
    private SQLiteDatabase database;
    private MyAdapter adapter;
    private View selectView;
    private static final String TABLE_NAME = "student";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_AGE = "age";
    private static final String COL_SCORE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework01);
        init();
        listener();
    }

    private void selectAll(){
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        list.clear();
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            int age = cursor.getInt(cursor.getColumnIndex(COL_AGE));
            int score = cursor.getInt(cursor.getColumnIndex(COL_SCORE));
            list.add(new Student(id,name,age, score));
        }
    }

    private void init() {
        btn_insert = (Button) findViewById(R.id.btn_insert_homework01);
        btn_delete = (Button) findViewById(R.id.btn_delete_homework01);
        edt_name = (EditText) findViewById(R.id.edt_name_homework01);
        edt_age = (EditText) findViewById(R.id.edt_age_homework01);
        edt_score = (EditText) findViewById(R.id.edt_score_homework01);
        lv = (ListView) findViewById(R.id.lv_homework01);
        list = new ArrayList<>();
        adapter = new MyAdapter();
        MyOpenHelper openHelper = new MyOpenHelper(getApplicationContext(), "data.db", null, 1);
        database = openHelper.getWritableDatabase();
        selectAll();
        lv.setAdapter(adapter);
    }

    private void listener() {
        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        lv.setOnItemClickListener(new adapterListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_insert_homework01:
                insert();
                break;
            case R.id.btn_delete_homework01:
                delete();
                break;
        }
    }

    private void delete() {
        if (selectItem>-1){
            int id = list.get(selectItem).getId();
            String[] whereArgs = {""+id};
            final String whereClause = "id = ?";
            database.delete(TABLE_NAME,whereClause,whereArgs);
            selectAll();
            adapter.notifyDataSetChanged();
            selectItem = -1;
        }else{
            Toast.makeText(this, "请选择要删除内容", Toast.LENGTH_SHORT).show();
        }
    }

    private void insert() {
        try {
            String name = edt_name.getText()+"";
            int age = Integer.parseInt(""+edt_age.getText());
            int score = Integer.parseInt(""+edt_score.getText());
            if (!"".equals(name)){
                if (age>17&&age<101){
                    if (score>-1&&score<101){
                        ContentValues values = new ContentValues();
                        values.put(COL_NAME,name);
                        values.put(COL_AGE,""+age);
                        values.put(COL_SCORE,""+score);
                        database.insert(TABLE_NAME,null,values);
                        selectAll();
                        adapter.notifyDataSetChanged();
                        selectItem = -1;
                    }else{
                        Toast.makeText(this, "分数范围0~100之间", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"年龄范围18~100之间", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"信息不能为空", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,"信息不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    class adapterListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectView!=null){
                selectView.setBackgroundColor(Color.TRANSPARENT);
            }
            selectView = view;
            selectItem = position;
            view.setBackgroundColor(R.color.homework01_select);
        }

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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.homework01_item,parent,false);
            TextView tv_id = (TextView) convertView.findViewById(R.id.tv_id_homework01_item);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name_homework01_item);
            TextView tv_age = (TextView) convertView.findViewById(R.id.tv_age_homework01_item);
            TextView tv_score = (TextView) convertView.findViewById(R.id.tv_score_homework01_item);
            tv_id.setText(list.get(position).getId()+"");
            tv_name.setText(list.get(position).getName());
            tv_age.setText(list.get(position).getAge()+"");
            tv_score.setText(list.get(position).getScore()+"");
            return convertView;
        }
    }
}
