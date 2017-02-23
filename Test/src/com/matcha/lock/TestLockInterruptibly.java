package com.matcha.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rd_xidong_ren on 2016/7/21.
 */
public class TestLockInterruptibly
{
    public static void main(String[] args)
    {
        Lock lock = new ReentrantLock(true);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    countDownLatch.await();
                    lock.lock();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }, "MatchaGetLockThread");
        Thread thread2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    countDownLatch.await();
                    lock.lockInterruptibly();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }, "MatchaGetLockInterruptiblyThread");
        Thread thread3 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    countDownLatch.await();
                    lock.lock();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }, "MatchaOtherGetLockThread");
        Thread thread4 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    countDownLatch.await();
                    thread2.interrupt();
                    thread3.interrupt();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }, "MatchaInterruptThread");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        countDownLatch.countDown();
    }
}
