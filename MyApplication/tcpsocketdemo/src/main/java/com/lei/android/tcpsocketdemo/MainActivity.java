package com.lei.android.tcpsocketdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Button btn_connect,btn_send;
    private TextView tv_text;
    private EditText edit_text;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private StringBuilder text = new StringBuilder();
    private String message;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_text.setText(text.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn_connect = (Button) findViewById(R.id.btn_open_socket);
        btn_send = (Button) findViewById(R.id.btn_send_message);
        tv_text = (TextView) findViewById(R.id.tv_text);
        edit_text = (EditText) findViewById(R.id.edit_text);
        btn_send.setEnabled(false);
    }

    public void toOpenSocket(View v) {
        new MyAsyncTask().execute();
    }

    public void toSendMessage(View v){
        message = edit_text.getText()+"";
        if (!"".equals(message)){
            new MyAsyncTask2().execute(message);
        }else{
            Toast.makeText(MainActivity.this, "内容为空", Toast.LENGTH_SHORT).show();
        }
    }

    class MyAsyncTask2 extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            text.append("我说："+s+"\n");
            handler.sendEmptyMessage(0x001);
        }

        @Override
        protected String doInBackground(String... params) {
            writer.println(params[0]);
            return params[0];
        }
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            if (socket.isConnected()){
                Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                btn_send.setEnabled(true);
                btn_connect.setEnabled(false);
                new MyThread().start();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            socketInit();
            return null;
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            String str = null;
            while (true) {
                try {
                    if ((str = reader.readLine())!=null){
                        text.append(str+"\n");
                        handler.sendEmptyMessage(0x001);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void socketInit(){
        try {
            socket = new Socket("172.27.35.8",12345);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
