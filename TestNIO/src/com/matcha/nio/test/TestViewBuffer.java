package com.matcha.nio.test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/18.
 */
public class TestViewBuffer
{
    public static void main(String[] args)
    {
        String str1 = "今天也是个好天气!";
        String str2 = "你好！世界！";
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);
        byteBuffer.put(str1.getBytes());
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        byteBuffer.put(str2.getBytes());
        readCharBuffer(charBuffer);
        readByteBuffer(byteBuffer);
    }

    private static void readCharBuffer(CharBuffer charBuffer)
    {
        int remaining = charBuffer.remaining();
        char[] chars = new char[remaining];
        charBuffer.get(chars);
        System.out.println(new String(chars));
    }

    private static void readByteBuffer(ByteBuffer byteBuffer)
    {
        int remaining = byteBuffer.remaining();
        byte[] bytes = new byte[remaining];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
    }
}
