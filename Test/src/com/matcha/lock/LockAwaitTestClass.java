package com.matcha.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rd_xidong_ren on 2016/7/20.
 */
public class LockAwaitTestClass
{
    public static void main(String[] args)
    {
        try
        {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Lock lock = new ReentrantLock(true);
            Condition condition = lock.newCondition();
            Thread thread1 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        countDownLatch.await();
                        lock.lock();
                        condition.await();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        lock.unlock();
                    }
                }
            }, "MatchaTestThread - " + 1);
            Thread thread2 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        countDownLatch.await();
                        lock.lock();
                        condition.signal();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    finally
                    {
                        lock.unlock();
                    }
                }
            }, "MatchaTestThread - " + 2);
            thread1.start();
            thread2.start();
            Thread.sleep(100);
            countDownLatch.countDown();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
