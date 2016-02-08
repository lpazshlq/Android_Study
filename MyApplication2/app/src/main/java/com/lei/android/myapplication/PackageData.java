package com.lei.android.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据加工
 */
public class PackageData {

    public enum HandleState {
        IsBody, IsHead, IsCrc
    }

    public void startInputThread(InputStream is) {
        isInput = true;
        isHandle = true;
        new InputThread(is).start();
        new HandleThread().start();
    }


    boolean isInput;

    private class InputThread extends Thread {
        InputStream is;
        byte[] data = new byte[4096];

        public InputThread(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            while (isInput) {
                if (null != is) {
                    try {
                        int size = is.read(data);
                        if (size > 0) {
                            byte[] newData = Arrays.copyOf(data, size);
                            datas.add(newData);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    List<byte[]> datas = new ArrayList<>();

    boolean isHandle;

    private class HandleThread extends Thread {

        @Override
        public void run() {
            while (isHandle) {
                if (datas.size() > 0) {
                    byte[] data = datas.remove(0);
                    handleData(data);
                }
            }
        }
    }

    int beginIndex = -1;

    int endIndex = -1;

    int leaveCount = 0;

    int currentIndex = -1;

    byte[] lastData;

    private void handleData(byte[] data) {
        byte[] currentData = mergeDataArr(lastData, data, leaveCount);
        int resultIndex = findHead(currentData, currentIndex);
        if (resultIndex > 0) {
            if (resultIndex + 11 <= currentData.length - 1) {
                byte[] crcArr = Arrays.copyOfRange(currentData, resultIndex, resultIndex + 3);
                byte[] lenArr = Arrays.copyOfRange(currentData, resultIndex + 4, resultIndex + 7);
                byte[] typeArr = Arrays.copyOfRange(currentData, resultIndex + 8, resultIndex + 11);
                int crc = byteToInt(crcArr);
                int len = byteToInt(lenArr);
                int type = byteToInt(typeArr);
            }
        } else if (resultIndex == 0) {
            currentIndex = data.length - 4;
        }
    }

    private int findHead(byte[] data, int currentIndex) {
        int i = 0;
        if (currentIndex > 0) {
            i = currentIndex;
        }
        for (; i < data.length - 3; i++) {
            if (data[i] == 0x00 && data[i + 1] == 0x20 && data[i + 2] == 0xff && data[i + 3] == 0xff) {
                if (i + 4 > data.length-1) {
                    return 0;
                }
                return i + 4;
            }
        }
        return -1;
    }

    private byte[] mergeDataArr(byte[] lastData, byte[] currentData, int leaveCount) {
        byte[] data = new byte[leaveCount + currentData.length];
        if (null != lastData && leaveCount > 0) {
            System.arraycopy(lastData, lastData.length - leaveCount - 1, data, 0, leaveCount);
            System.arraycopy(currentData, 0, data, leaveCount, currentData.length);
            return data;
        }
        return currentData;
    }

    private byte[] getData(byte[] currentData,int startIndex,int endIndex) {
        return Arrays.copyOfRange(currentData,startIndex,endIndex);
    }

    private int byteToInt(byte[] byteArr) {
        int v0 = (byteArr[0] & 0xff) << 24;
        int v1 = (byteArr[1] & 0xff) << 16;
        int v2 = (byteArr[2] & 0xff) << 8;
        int v3 = (byteArr[3] & 0xff);
        return v0 + v1 + v2 + v3;
    }
}
