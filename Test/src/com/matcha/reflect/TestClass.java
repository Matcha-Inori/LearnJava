package com.matcha.reflect;

import com.matcha.reflect.app.B;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/24.
 */
public class TestClass
{
    public static void main(String[] args)
    {
        try
        {
            Method methodB1 = B.class.getDeclaredMethod("methodB1", String.class, int.class);
            Method methodA1 = B.class.getMethod("methodA1", String.class);
//            Method methodA1_1 = B.class.getDeclaredMethod("methodA1", String.class);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
