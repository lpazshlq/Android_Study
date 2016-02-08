package com.lei.android.myapplication;

import java.util.zip.CRC32;

/**
 * Created by MBENBEN on 2016/2/1.
 */
public class CrcUtil {
    public static boolean checkCRC32(byte[] data,int crc) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc == crc32.getValue();
    }
}
