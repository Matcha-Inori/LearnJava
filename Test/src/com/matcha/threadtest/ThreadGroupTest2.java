package com.matcha.threadtest;

import com.matcha.threadtest.factory.ThreadGroupThreadFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/27.
 */
public class ThreadGroupTest2
{
    public static void main(String[] args)
    {
        ThreadGroupThreadFactory threadGroupThreadFactory = new ThreadGroupThreadFactory();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        int threadCount = 10;
        for(int index = 0;index < threadCount;index++)
        {
            threadGroupThreadFactory.newThread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        countDownLatch.await();
                        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                    }
                    catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                countDownLatch.countDown();
            }
        }, "countDownThread").start();
        try
        {
            threadGroupThreadFactory.waitOnThreadGroup(true);
            threadGroupThreadFactory.waitOnThreadGroup(false);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        threadGroupThreadFactory.destroyThreadGroup();
    }
}
