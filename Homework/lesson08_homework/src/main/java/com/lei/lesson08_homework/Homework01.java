package com.lei.lesson08_homework;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Homework01 extends Activity implements View.OnClickListener {
    private Button btn_write,btn_read;
    private TextView tv;
    private EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework01);
        init();
        listener();
    }

    private void init() {
        btn_write = (Button)findViewById(R.id.btn_write);
        btn_read = (Button)findViewById(R.id.btn_read);
        tv = (TextView) findViewById(R.id.tv_homework01);
        edt = (EditText)findViewById(R.id.edt_homework01);
    }

    private void listener() {
        btn_write.setOnClickListener(this);
        btn_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_write:
                String text = edt.getText()+"";
                Log.i("test","write");
                if (!text.equals("")){
                    String sdcardState = Environment.getExternalStorageState();
                    if (sdcardState.equals(Environment.MEDIA_MOUNTED)){
                        try {
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/sdcard/DCIM/a.txt")));
                            writer.write(text);
                            writer.newLine();
                            writer.flush();
                            writer.close();
                            Toast.makeText(Homework01.this,"success",Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(Homework01.this,"内存卡不可用",Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Toast.makeText(Homework01.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_read:
                String sdcardState = Environment.getExternalStorageState();
                if (sdcardState.equals(Environment.MEDIA_MOUNTED)){
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/DCIM/a.txt")));
                        String line;
                        if ((line = reader.readLine())!=null){
                            tv.setText(line);
                        }else{
                            Toast.makeText(Homework01.this,"读取不到信息", Toast.LENGTH_SHORT).show();
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(Homework01.this,"内存卡不可用",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
