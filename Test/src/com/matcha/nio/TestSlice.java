package com.matcha.nio;

import java.nio.CharBuffer;

/**
 * Created by Matcha on 16/9/7.
 */
public class TestSlice
{
    public static void main(String[] args)
    {
        CharBuffer bufferSrc = CharBuffer.allocate(10);
        String str = "1234567890";
        bufferSrc.put(str);
        bufferSrc.flip();
        bufferSrc.position(3).limit(6).mark().position(5);
        CharBuffer otherBuffer = bufferSrc.slice();
        bufferSrc.clear();
        System.out.println("src buffer");
        while(bufferSrc.hasRemaining())
        {
            System.out.print(bufferSrc.get());
        }
        System.out.println("\nother buffer");
        while(otherBuffer.hasRemaining())
        {
            System.out.print(otherBuffer.get());
        }
    }
}
