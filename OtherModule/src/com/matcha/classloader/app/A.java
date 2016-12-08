package com.matcha.classloader.app;

/**
 * Created by Matcha on 2016/11/28.
 */
public class A
{
    public void print()
    {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + " - print B");
    }
}
