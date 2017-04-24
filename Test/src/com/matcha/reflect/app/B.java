package com.matcha.reflect.app;

/**
 * Created by Administrator on 2017/4/24.
 */
public class B extends A
{
    public void methodB1(String param1, int param2)
    {
        System.out.println(String.format("%s_%d", param1, param2));
    }
}
