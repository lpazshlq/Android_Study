package com.lei.socket_client;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.io.Serializable;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client extends AppCompatActivity {
    private Button btn_connect,btn_send_message,btn_disconnect;
    private EditText edt_message;
    private TextView tv_text;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private StringBuilder text = new StringBuilder();
    private boolean isConnected;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_text.setText(text);
        }
    };

    private MyListener listener = new MyListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        init();
        listener();
    }

    private void init() {
        btn_connect = (Button) findViewById(R.id.btn_connect_client);
        btn_send_message = (Button) findViewById(R.id.btn_send_message_client);
        btn_disconnect = (Button) findViewById(R.id.btn_disconnect_client);
        edt_message = (EditText) findViewById(R.id.edt_message_client);
        tv_text = (TextView) findViewById(R.id.tv_text);
    }

    private void listener() {
        btn_connect.setOnClickListener(listener);
        btn_send_message.setOnClickListener(listener);
        btn_disconnect.setOnClickListener(listener);
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void>{
        IOException ioException;

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(Client.this, "客户端连接成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Toast.makeText(Client.this, ioException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new Socket("192.168.110.222",8888);
                new MyClientThread().start();
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            } catch (IOException e) {
                ioException = e;
                publishProgress();
            }
            return null;
        }
    }

    class CheckFile implements Serializable{
        int head;
        int crc;
        String json;

    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_connect_client:
                    if (!isConnected){
                        isConnected = true;
                        new MyAsyncTask().execute();
                    }
                    break;
                case R.id.btn_send_message_client:
                    if (isConnected){
                        String message = edt_message.getText()+"";
                        if (!"".equals(message)){
                            writer.println(message);
                        }
                    }
                    break;
                case R.id.btn_disconnect_client:
                    try {
                        if (!isConnected){
                            isConnected = false;
                            reader.close();
                            writer.close();
                            socket.close();
                            reader = null;
                            writer = null;
                            socket = null;
                        }
                    } catch (IOException e) {
                        Toast.makeText(Client.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    class MyClientThread extends Thread {
        @Override
        public void run() {
            try {
                while (isConnected){
                    String line;
                    while ((line = reader.readLine())!=null){
                        Date now = new Date();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        text.append(socket.getInetAddress().getHostName())
                                .append("   ")
                                .append(format.format(now))
                                .append("\n")
                                .append(line)
                                .append("\n");
                        handler.sendEmptyMessage(0x001);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
