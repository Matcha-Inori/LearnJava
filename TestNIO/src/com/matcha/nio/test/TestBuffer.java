package com.matcha.nio.test;

import java.nio.CharBuffer;

/**
 * Created by Administrator on 2016/9/6.
 */
public class TestBuffer
{
    private static final String[] strings;
    private static int index;

    static
    {
        strings = new String[]{
            "This is normal string!!!",
            "Test String",
            "Riven",
            "Matcha",
            "测试中文"
        };
        index = 0;
    }

    public static void main(String[] args)
    {
        CharBuffer charBuffer = CharBuffer.allocate(100);
        while(fillBuffer(charBuffer))
        {
            charBuffer.flip();
            drainBuffer(charBuffer);
            charBuffer.clear();
        }
    }

    private static boolean fillBuffer(CharBuffer charBuffer)
    {
        if(index >= strings.length)
            return false;
        String str = strings[index++];
        char[] chars = str.toCharArray();
        for(char oneChar : chars)
            charBuffer.put(oneChar);
        return true;
    }

    private static void drainBuffer(CharBuffer charBuffer)
    {
        while(charBuffer.hasRemaining())
            System.out.print(charBuffer.get());
        System.out.println("");
    }
}
