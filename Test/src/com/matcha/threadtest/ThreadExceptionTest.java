package com.matcha.threadtest;

import com.matcha.threadtest.handler.MatchaExceptionHandler;

/**
 * Created by Administrator on 2016/12/8.
 */
public class ThreadExceptionTest
{
    public static void main(String[] args)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                throw new RuntimeException();
            }
        };
        thread.setUncaughtExceptionHandler(new MatchaExceptionHandler());
        thread.start();
    }
}
