package com.lei.android.myapplication;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SocketUtil.HandleListener {

    private JsonUtil jsonUtil = new JsonUtil();

    private SocketUtil socketUtil = new SocketUtil();

    private static int sampleRate = 48000;
    private static int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    private static int EncodingBitRate = AudioFormat.ENCODING_PCM_16BIT;


    private AudioRecord audioRecord = null;
    private int recBufSize = 0;
    private Thread recordingThread = null;
    private Thread getResultThread = null;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_stop_record).setEnabled(false);
        JNI.init_Featureget();
        socketUtil.setHandleListener(this);
    }

    public void createAudioRecord() {
        recBufSize = AudioRecord.getMinBufferSize(sampleRate,
                channelConfiguration, EncodingBitRate);


        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                channelConfiguration, EncodingBitRate, recBufSize);
    }

    private void sendAudioDataToJni() {
        try {
            connectService();
            byte data[] = new byte[recBufSize];
            int read;
            while (isRecording) {
                read = audioRecord.read(data, 0, recBufSize);
                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    JNI.inputPCM(data, data.length, sampleRate, 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int i = 0;

    public void toStartRecord(View v) {
        v.setEnabled(false);
        findViewById(R.id.btn_stop_record).setEnabled(true);
        createAudioRecord();
        audioRecord.startRecording();
        isRecording = true;
        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sendAudioDataToJni();
            }
        }, "AudioRecorder Thread");

        getResultThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRecording) {
                    try {
                        Thread.sleep(500);
                        byte[] data = JNI.getPcmResult();
//                        Log.i("test", Arrays.toString(data));
                        if (null != data) {
                            byte[] res = jsonUtil.setHeadLenCrcType(jsonUtil.setJsonData("12345", 12345, "12345", jsonUtil.getAudioJson(data)), 0);
                            if (null != os) {
                                    os.write(res);
                                    os.flush();
                            }
                        }

                    } catch (InterruptedException | JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "GetResult Thread");

        recordingThread.start();

        getResultThread.start();
    }

    public void toStopRecord(View v) {
        v.setEnabled(false);
        findViewById(R.id.btn_start_record).setEnabled(true);
        if (null != audioRecord) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            recordingThread = null;
            getResultThread = null;
        }
    }

    private Socket socket;

    private PrintWriter writer;

    private BufferedReader reader;

    private InputStream is;

    private OutputStream os;

    private void connectService() throws IOException {
        socket = new Socket("192.168.110.221", 1000);
        writer = new PrintWriter(socket.getOutputStream(), true);
        os = socket.getOutputStream();
        is = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final byte[] data = new byte[4096];

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (null != is) {
                        try {
                            int size = is.read(data);
                            if (size > 0) {
                                byte[] byteArr = takeData(data, size);
                                list.add(byteArr);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (list.size() > 0) {
                        byte[] data = list.remove(0);
                        socketUtil.handle(data);
                        Log.i("test", Arrays.toString(data));
                    }
                }
            }
        }.start();
    }


    ArrayList<byte[]> list = new ArrayList<>();

    @Override
    public void success(JSONObject jsonObject) {
        Log.i("test", "Json接收成功：" + jsonObject.toString());
    }

    @Override
    public void failure() {
        Log.i("test", "Json接收失败");
    }

    public byte[] takeData(byte[] data, int size) {
        return Arrays.copyOf(data, size);
    }

    public String toData(byte[] array) {
        if (array == null) {
            return "null";
        }
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder(array.length * 6);
        sb.append('[');
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(",");
            sb.append(array[i]);
        }
        sb.append(']');
        return sb.toString();
    }

}
