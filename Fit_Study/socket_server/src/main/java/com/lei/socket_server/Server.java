package com.lei.socket_server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends AppCompatActivity {
    private ServerSocket serverSocket;
    private ArrayList<Socket> list_socket = new ArrayList<>();

    private Button btn_open,btn_close;
    private TextView tv_text;

    private MyListener listener = new MyListener();
    private MyServerThread myServerThread;

    private boolean isServerClosed = true;
    private StringBuilder text = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        init();
        listener();
    }

    private void init() {
        btn_open = (Button) findViewById(R.id.btn_open_server);
        btn_close = (Button) findViewById(R.id.btn_close_server);
        tv_text = (TextView) findViewById(R.id.tv_text_server);
    }

    private void listener() {
        btn_open.setOnClickListener(listener);
        btn_close.setOnClickListener(listener);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_open_server:
                    if (isServerClosed){
                        isServerClosed = false;
                        try {
                            serverSocket = new ServerSocket(8888);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        myServerThread = new MyServerThread();
                        myServerThread.start();
                        Toast.makeText(Server.this, "开启服务端", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Server.this, "服务端已开启", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_close_server:
                    if (!isServerClosed){
                        isServerClosed = true;
                        list_socket.clear();
                        Toast.makeText(Server.this, "关闭服务端", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Server.this, "服务端已关闭", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    class MyServerThread extends Thread {

        @Override
        public void run() {
            while (!isServerClosed){
                try {
                    Socket socket = serverSocket.accept();
                    list_socket.add(socket);
                    new MySocketThread(socket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MySocketThread extends Thread {
        Socket socket;
        BufferedReader reader;
        PrintWriter writer;
        boolean isClientConnected = true;

        public MySocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                String line = null;
                while (isClientConnected){
                    while ((line = reader.readLine())!=null){
//                        text.append(line+"\n");
//                        tv_text.setText(text);
                        writer.println(line.toUpperCase());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    reader.close();
                    writer.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
