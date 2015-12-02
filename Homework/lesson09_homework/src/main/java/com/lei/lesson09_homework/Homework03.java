package com.lei.lesson09_homework;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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

public class Homework03 extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private ListView lv;
    private Button btn_insert,btn_delete;
    private EditText edt_name,edt_age,edt_score;
    private ArrayList<Student> list;
    private MyAdapter adapter;
    private View selectedView;
    private ContentResolver resolver;
    private int selectedPosition = -1;
    private static final Uri uri = Uri.parse("content://" + "com.lei.lesson09_homework_provider");
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_AGE = "age";
    private static final String COL_SCORE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework03);
        init();
        listener();
    }

    private void init() {
        btn_delete = (Button) findViewById(R.id.btn_delete_homework03);
        btn_insert = (Button) findViewById(R.id.btn_insert_homework03);
        edt_name = (EditText) findViewById(R.id.edt_name_homework03);
        edt_age = (EditText) findViewById(R.id.edt_age_homework03);
        edt_score = (EditText) findViewById(R.id.edt_score_homework03);
        lv = (ListView) findViewById(R.id.lv_homework03);
        list = new ArrayList<>();
        adapter = new MyAdapter();
        resolver = getContentResolver();
        selectAll();
        lv.setAdapter(adapter);
    }

    private void listener() {
        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_insert_homework03:
                insert();
                break;
            case R.id.btn_delete_homework03:
                delete();
                break;
        }
    }

    private void selectAll() {
        Cursor cursor = resolver.query(uri, null, null, null, null);
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            int age = cursor.getInt(cursor.getColumnIndex(COL_AGE));
            int score = cursor.getInt(cursor.getColumnIndex(COL_SCORE));
            list.add(new Student(id,name,age,score));
        }
    }

    private void delete() {
        if (selectedPosition > -1){
            String where = "id = ?";
            String[] selectionArgs = {""+list.get(selectedPosition).getId()};
            resolver.delete(uri,where,selectionArgs);
            list.clear();
            selectAll();
            adapter.notifyDataSetChanged();
            selectedPosition = -1;
        }else{
            Toast.makeText(this,"请选择要删除内容", Toast.LENGTH_SHORT).show();
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
                        values.put(COL_AGE, "" + age);
                        values.put(COL_SCORE, "" + score);
                        resolver.insert(uri, values);
                        list.clear();
                        selectAll();
                        adapter.notifyDataSetChanged();
                        selectedPosition = -1;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (selectedView!=null){
            selectedView.setBackgroundColor(Color.TRANSPARENT);
        }
        selectedPosition = position;
        selectedView = view;
        view.setBackgroundColor(R.color.homework01_select);
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
            convertView = getLayoutInflater().inflate(R.layout.homework03_item,parent,false);
            TextView tv_id = (TextView) convertView.findViewById(R.id.tv_id_homework03_item);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name_homework03_item);
            TextView tv_age = (TextView) convertView.findViewById(R.id.tv_age_homework03_item);
            TextView tv_score = (TextView) convertView.findViewById(R.id.tv_score_homework03_item);
            Student student = list.get(position);
            tv_id.setText(student.getId()+"");
            tv_name.setText(student.getName());
            tv_age.setText(student.getAge()+"");
            tv_score.setText(student.getScore()+"");
            return convertView;
        }
    }
}
