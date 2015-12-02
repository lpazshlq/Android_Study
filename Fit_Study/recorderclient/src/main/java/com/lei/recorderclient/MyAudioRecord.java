package com.lei.recorderclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class MyAudioRecord extends Thread {
    protected AudioRecord m_in_rec;
    /**
     * 数据采集数组长度
     */
    protected int m_in_buf_size;
    /**
     * 数据采集数组
     */
    protected byte[] m_in_bytes;
    /**
     * 正在运行标识
     */
    protected boolean m_keep_running;

    protected Socket socket;
    /**
     *
     */
    protected DataOutputStream dout;
    /**
     * 录音缓存数组存放容器（每当集合长度等于2，数据传输）
     */
    protected LinkedList<byte[]> m_in_q;

    public void run() {
        try {
            byte[] bytes_pkg;
            m_in_rec.startRecording();
            while (m_keep_running) {
                m_in_rec.read(m_in_bytes, 0, m_in_buf_size);
                bytes_pkg = m_in_bytes.clone();
                if (m_in_q.size() >= 2) {
                    dout.write(m_in_q.removeFirst(), 0, m_in_q.removeFirst().length);
                }
                m_in_q.add(bytes_pkg);
            }
            m_in_rec.stop();
            m_in_rec = null;
            m_in_bytes = null;
            dout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init() {
        m_in_buf_size = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);

        m_in_rec = new AudioRecord(MediaRecorder.AudioSource.MIC,//音频源
                44100,//采样率 Hz
                AudioFormat.CHANNEL_OUT_STEREO,//声道数
                AudioFormat.ENCODING_PCM_16BIT,//精度
                m_in_buf_size);//缓存数组长度

        m_in_bytes = new byte[m_in_buf_size];
        m_keep_running = true;
        m_in_q = new LinkedList<>();
        try {
            socket = new Socket("10.2.1.128", 8888);
            dout = new DataOutputStream(socket.getOutputStream());
            //new Thread(R1).start();
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void free() {
        m_keep_running = false;
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            Log.d("sleep exceptions...\n", "");
        }
    }

}
