package com.matcha.threadtest.handler;

/**
 * Created by Administrator on 2016/12/8.
 */
public class MatchaExceptionHandler implements Thread.UncaughtExceptionHandler
{
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        System.out.println("MatchaExceptionHandler: ");
        e.printStackTrace();
    }
}
