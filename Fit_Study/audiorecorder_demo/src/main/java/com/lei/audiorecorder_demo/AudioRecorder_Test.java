package com.lei.audiorecorder_demo;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 主要为了获取录音PCM数据数组
 */
public class AudioRecorder_Test extends ActionBarActivity {
    TextView tv_date;

    AudioRecord audioRecord;
    private int recBufSize = 0;

    private static int frequency = 44100;
    private static int channelConfiguration = AudioFormat.CHANNEL_IN_STEREO;
    private static int EncodingBitRate = AudioFormat.ENCODING_PCM_16BIT;

    private boolean isRecording = false;
    private char[] cs;
    private byte[] bytes;

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            tv_date.setText(Arrays.toString(cs));
//            Log.i("test",Arrays.toString(cs));
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_recorder_test);
        tv_date = (TextView) findViewById(R.id.tv_date);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_record:
                isRecording = true;
                createAudioRecord();
                audioRecord.startRecording();
                new Thread(){
                    @Override
                    public void run() {
//                        cs = getAudioData();
//                        handler.sendEmptyMessage(0x001);
                        getAudioSocket();
                    }
                }.start();
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            isRecording = false;
                            audioRecord.stop();
                            audioRecord.release();
                            audioRecord = null;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }

    public void createAudioRecord() {
        recBufSize = AudioRecord.getMinBufferSize(frequency,
                channelConfiguration, EncodingBitRate);


        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                channelConfiguration, EncodingBitRate, recBufSize);
    }

    private char[] getAudioData() {
        char[] ch;
        byte[] data = new byte[recBufSize];
        byte[] alldata = new byte[0];
        int read = 0;
        while (isRecording) {
            read = audioRecord.read(data, 0, recBufSize);
            if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                alldata = concat(alldata,data);
            }
        }
        ch = getChars(alldata);
        return ch;
    }

    private void getAudioSocket() {
        try {
            Socket socket = new Socket("192.168.10.106",8888);
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            byte[] data = new byte[recBufSize];
            int read = 0;
            while (isRecording) {
                read = audioRecord.read(data, 0, recBufSize);
                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    bos.write(data);
                    bos.flush();
                }
            }
        } catch (IOException e) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            e.printStackTrace();
        }
    }

    private byte[] concat(byte[] a, byte[] b) {
        byte[] c= new byte[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    private char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }
}
