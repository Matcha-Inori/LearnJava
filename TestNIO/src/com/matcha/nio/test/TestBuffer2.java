package com.matcha.nio.test;

import java.nio.BufferOverflowException;
import java.nio.CharBuffer;

/**
 * Created by Administrator on 2016/9/7.
 */
public class TestBuffer2
{
    public static void main(String[] args)
    {
        try
        {
            int size = 10;
            CharBuffer charBuffer = null;
            try
            {
                charBuffer = CharBuffer.allocate(size);
                String str = "123456789011";
                if(!charBuffer.isReadOnly())
                    charBuffer.put(str);
            }
            catch(BufferOverflowException e)
            {
                e.printStackTrace();
            }
            char[] chars = new char[size];
            charBuffer.get(chars);
            String readStr = new String(chars);
            System.out.println(readStr);
        }
        catch(RuntimeException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
