package com.lei.android.myapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Created by MBENBEN on 2016/2/2.
 */
public class SocketUtil {

    private HandleListener listener;

    private int startIndex = -1;

    private int endIndex = -1;

    private int currentIndex = 0;

    private byte[] lastData;

    private byte[] currentData;

    public void handle(byte[] newData) {
        currentData = mergeByteArr(newData, lastData);
        handleData();
    }

    private byte[] mergeByteArr(byte[] newData, byte[] lastData) {
        byte[] data;
        if (null != lastData) {
            data = new byte[lastData.length + newData.length];
            System.arraycopy(lastData, 0, data, 0, lastData.length);
            System.arraycopy(newData, 0, data, lastData.length, newData.length);
            return data;
        }
        return newData;
    }

    private void handleData() {
        if (startIndex < 0) {//第一个头标识未找到
            int index = findHeadIndex(currentData, currentIndex);
            if (index >= 0) {//找到第一个头标识
                Log.i("test", "找到第一个头标识");
                startIndex = index;
                currentIndex = startIndex;
                handleData();//递归处理
            } else {
                init();
            }
        } else if (startIndex > 0) {//第一个头标识已找到
            int index = findHeadIndex(currentData, currentIndex);
            if (index >= 0) {//找到第二个头标识
                endIndex = index - 5;//确认末尾标识位
                byte[] jsonData = Arrays.copyOfRange(currentData, startIndex, endIndex + 1);
                JSONObject result = getJsonObject(checkData(jsonData));//检查并获取json数据
                if (null != result) {
                    if (null != listener) {
                        listener.success(result);
                    }
                } else {
                    if (null != listener) {
                        listener.failure();
                    }
                }
                startIndex = index;
                endIndex = -1;
                currentIndex = startIndex;
                handleData();//递归处理
            } else {//未找到第二个头标识
                Log.i("test", "未找到第二个头标识");
                if (startIndex + 11 < currentData.length) {
                    byte[] jsonData = Arrays.copyOfRange(currentData, startIndex, currentData.length);
                    JSONObject result = getJsonObject(checkData(jsonData));
                    if (null != result) {
                        if (null != listener) {
                            listener.success(result);
                        }

                        init();

                    } else {
                        lastData = Arrays.copyOfRange(currentData, startIndex - 4, currentData.length);
                        startIndex = 4;
                        currentIndex = lastData.length - 3;
                    }
                } else {
                    Log.i("test", "保存数据");
                    lastData = Arrays.copyOfRange(currentData, startIndex - 4, currentData.length);
                }
            }

        }
    }

    private int findHeadIndex(byte[] currentData, int currentIndex) {
        int i = 0;
        if (currentIndex > 0) {
            i = currentIndex;
        }
        for (; i < currentData.length - 4; i++) {
            if (currentData[i] == 0 && currentData[i + 1] == 32 && currentData[i + 2] == -1 && currentData[i + 3] == -1) {
                return i + 4;
            }
        }
        return -1;
    }

    private String checkData(byte[] data) {
        if (data.length >= 12) {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            int len = buffer.getInt();
            int crc = buffer.getInt();
            int type = buffer.getInt();
            Log.i("test", "len" + len);
            Log.i("test", "dataLen" + data.length);
            if (len == data.length - 12 && len != 0) {
                byte[] jsonByteArr = Arrays.copyOfRange(data, 12, data.length);
                if (checkCRC32(jsonByteArr, crc)) {
                    String json;
                    try {
                        json = new String(jsonByteArr, "utf-8");
                        return json;
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    private JSONObject getJsonObject(String data) {
        if (null != data) {
            JSONObject result;
            try {
                result = new JSONObject(data);
                return result;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    private boolean checkCRC32(byte[] data, int crc) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        int c = (int) crc32.getValue();
        Log.i("test", "crc = " + crc + " , value = " + c);
        return crc == c;
    }

    private int byteToInt(byte[] byteArr) {
        int v0 = (byteArr[0] & 0xff) << 24;
        int v1 = (byteArr[1] & 0xff) << 16;
        int v2 = (byteArr[2] & 0xff) << 8;
        int v3 = (byteArr[3] & 0xff);
        return v0 + v1 + v2 + v3;
    }

    private int bytesToInt(byte[] b) {
        int mask = 0xff;
        int temp = 0;
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res <<= 8;
            temp = b[i] & mask;
            res |= temp;
        }
        return res;
    }

    public static int byte2Int(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }

    private void init() {
        lastData = null;
        currentIndex = 0;
        startIndex = -1;
        endIndex = -1;
    }

    public void setHandleListener(HandleListener listener) {
        this.listener = listener;
    }

    public interface HandleListener {
        public void success(JSONObject jsonObject);

        public void failure();
    }

}
