package com.lei.recordserver;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class MyServer {
    private ServerSocket serverSocket;
    private int playBufSize = 0;
    private AudioTrack audioTrack = null;

    {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            Log.i("IOException", e.getMessage());
        }
    }

    public void startServer() throws IOException {
        Socket socket = serverSocket.accept();
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        createAudioTrack();
        byte[] datas = new byte[playBufSize];
        audioTrack.play();
        byte[] temps;
        int a = 0;
        while (true) {
            while (dis.read(datas) > 0) {
                temps = datas.clone();
                audioTrack.write(temps, 0, temps.length);
            }
            a++;
            Log.i("test",a+"");
        }
//        audioTrack.stop();
//        audioTrack = null;
//        dis.close();
//        socket.close();
//        startServer();
    }

    public void createAudioTrack() {
        int frequency = 44100;
        int channelConfiguration = AudioFormat.CHANNEL_IN_STEREO;
        int encodingBitRate = AudioFormat.ENCODING_PCM_16BIT;

        playBufSize = AudioTrack.getMinBufferSize(frequency,
                channelConfiguration, encodingBitRate);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency,
                channelConfiguration, encodingBitRate,
                playBufSize, AudioTrack.MODE_STREAM);
    }
}
