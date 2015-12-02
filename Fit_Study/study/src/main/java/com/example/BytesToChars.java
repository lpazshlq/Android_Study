package com.example;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * byte[]和char[]之间的转换
 */
public class BytesToChars {

    private byte[] getBytes (char[] chars) {
        Charset cs = Charset.forName ("UTF-8");
        CharBuffer cb = CharBuffer.allocate (chars.length);
        cb.put (chars);
        cb.flip ();
        ByteBuffer bb = cs.encode (cb);

        return bb.array();
    }
    private char[] getChars (byte[] bytes) {
        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put (bytes);
        bb.flip ();
        CharBuffer cb = cs.decode (bb);

        return cb.array();
    }

}
