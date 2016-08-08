package com.matcha.semaphore;

import com.matcha.semaphore.thread.GetConnectionRunnable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TestSemaphore
{
    public static void main(String[] args)
    {
        Semaphore semaphore = new Semaphore(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        int getConnectionCount = 1000;
        Thread thread = null;
        for(int i = 0;i < getConnectionCount;i++)
        {
            thread = new Thread(new GetConnectionRunnable(semaphore, countDownLatch),
                    "Get-Connection-Thread-" + i);
            thread.start();
        }
        countDownLatch.countDown();
    }
}
