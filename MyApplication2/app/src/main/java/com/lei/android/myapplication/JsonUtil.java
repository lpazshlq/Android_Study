package com.lei.android.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Created by MBENBEN on 2016/1/25.
 */
public class JsonUtil {
    String left = "{\"crc\":11,\"data\":{\"reqBody\":{\"audio\":";

    String right = "},\"reqToken\":\"11111\",\"reqType\":\"send_audio_data\"},\"dataLen\":123,\"type\":0}";


    public String getJsonData(String ddd) {
        return left + ddd + right;
    }

    public JSONObject setJsonData(String reqToken, int uid, String reqType, JSONObject reqBody) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("reqToken", reqToken);
        object.put("reqUid", uid);
        object.put("reqType", reqType);
        object.put("reqBody", reqBody);
        return object;
    }

    public JSONObject getAudioJson(byte[] audio) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("audio", Arrays.toString(audio));
        return object;
    }


    public byte[] setHeadLenCrcType(JSONObject jsonObject, int type) throws UnsupportedEncodingException {
        String str = jsonObject.toString();
        byte[] jsonData = str.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(jsonData.length + 16);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int len = jsonData.length;
        int crc = getBodyCrc(jsonData);
        byte[] headBytes = int2bytes(2162687);
//        byte[] lenBytes = int2bytesD(len);
//        byte[] crcBytes = int2bytesD(crc);
//        byte[] typeBytes = int2bytesD(type);
        buffer.put(headBytes);
        buffer.putInt(len);
        buffer.putInt(crc);
        buffer.putInt(type);
//        buffer.put(lenBytes);
//        buffer.put(crcBytes);
//        buffer.put(typeBytes);
        buffer.put(jsonData);
        return buffer.array();
    }

    public int getBodyCrc(byte[] jsonData) {
        CRC32 crc32 = new CRC32();
        crc32.update(jsonData);
        return (int) crc32.getValue();
    }

    public byte[] int2bytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 24) & 0xff);// 说明一
        result[1] = (byte) ((num >>> 16) & 0xff);
        result[2] = (byte) ((num >>> 8) & 0xff);
        result[3] = (byte) ((num >>> 0) & 0xff);
        return result;
    }

    public byte[] int2bytesD(int num) {
        byte[] result = new byte[4];
        result[3] = (byte) ((num >>> 24) & 0xff);// 说明一
        result[2] = (byte) ((num >>> 16) & 0xff);
        result[1] = (byte) ((num >>> 8) & 0xff);
        result[0] = (byte) ((num >>> 0) & 0xff);
        return result;
    }

    public byte[] intToBytesArr(int i) {
        byte[] a = new byte[4];
        a[0] = (byte) (0xff & i);
        a[1] = (byte) ((0xff00 & i) >> 8);
        a[2] = (byte) ((0xff0000 & i) >> 16);
        a[3] = (byte) ((0xff000000 & i) >> 24);
        return a;
    }

    public byte[] intToBytes(int i) {
        byte[] a = new byte[4];
        a[3] = (byte) (0xff & i);
        a[2] = (byte) ((0xff00 & i) >> 8);
        a[1] = (byte) ((0xff0000 & i) >> 16);
        a[0] = (byte) ((0xff000000 & i) >> 24);
        return a;
    }
}
