package com.lei.android.myapplication;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

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
    }

    public void createAudioRecord() {
        recBufSize = AudioRecord.getMinBufferSize(sampleRate,
                channelConfiguration, EncodingBitRate);


        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                channelConfiguration, EncodingBitRate, recBufSize);
    }

    private void sendAudioDataToJni() {
        byte data[] = new byte[recBufSize];
        int read;
        while (isRecording) {
            read = audioRecord.read(data, 0, recBufSize);
            if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                JNI.inputPCM(data, data.length, sampleRate, 1);
            }
        }
    }

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
                while (isRecording){
                    try {
                        Thread.sleep(500);
                        char[] data = JNI.getResult();
                        Log.i("test", Arrays.toString(data));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"GetResult Thread");

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
        }
    }

    private byte[] getBytes (char[] chars) {
        Charset cs = Charset.forName ("UTF-8");
        CharBuffer cb = CharBuffer.allocate (chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode (cb);

        return bb.array();
    }

    private char[] getChars (byte[] bytes) {
        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode (bb);

        return cb.array();
    }
}
