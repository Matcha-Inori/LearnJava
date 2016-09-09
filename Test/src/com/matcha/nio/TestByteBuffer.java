package com.matcha.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/9.
 */
public class TestByteBuffer
{
    private static boolean useChar = false;
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        String str1 = "今天也是美好的一天!";
        String str2 = "侦测到在途的聚变打击!";
        writeStrWithByte(byteBuffer, str1);
        byteBuffer.mark();
        writeStrWithByte(byteBuffer, str2);
        byteBuffer.reset();
        byteBuffer.flip();
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
//        byteBuffer.clear();

        printStrWithByte(charBuffer);
        printStrWithByte(byteBuffer);
    }

    private static void printStrWithByte(CharBuffer charBuffer)
    {
        if(!useChar)
            return;
        System.out.println("print char buffer");
        while(charBuffer.hasRemaining())
            System.out.print(charBuffer.get());
        System.out.println();
    }

    private static void printStrWithByte(ByteBuffer byteBuffer)
    {
        if(useChar)
            printChar(byteBuffer);
        else
            printStr(byteBuffer);
    }

    private static void printStr(ByteBuffer byteBuffer)
    {
        System.out.println("print byte buffer");
        int remaining = byteBuffer.remaining();
        if(remaining <= 0)
            return;
        byte[] bytes = new byte[remaining];
        byteBuffer.get(bytes);
        String str = new String(bytes, charset);
        System.out.println(str);
        System.out.println();
    }

    private static void printChar(ByteBuffer byteBuffer)
    {
        System.out.println("print byte buffer");
        while(byteBuffer.hasRemaining())
            System.out.print(byteBuffer.getChar());
        System.out.println();
    }

    private static ByteBuffer writeStrWithByte(ByteBuffer byteBuffer, String str)
    {
        if(useChar)
            return writeChars(byteBuffer, str);
        else
            return writeStr(byteBuffer, str);
    }

    private static ByteBuffer writeStr(ByteBuffer byteBuffer, String str)
    {
        if(byteBuffer.isReadOnly())
            return byteBuffer;
        if(byteBuffer.remaining() < str.length())
            return byteBuffer;
        byteBuffer.put(str.getBytes(charset));
        return byteBuffer;
    }

    private static ByteBuffer writeChars(ByteBuffer byteBuffer, String str)
    {
        if(byteBuffer.isReadOnly())
            return byteBuffer;
        char[] chars = str.toCharArray();
        int index = 0;
        while(byteBuffer.hasRemaining() && index < chars.length)
            byteBuffer.putChar(chars[index++]);
        return byteBuffer;
    }
}
