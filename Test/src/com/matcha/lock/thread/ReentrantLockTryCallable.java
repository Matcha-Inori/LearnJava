package com.matcha.lock.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by rd_xidong_ren on 2016/7/21.
 */
public class ReentrantLockTryCallable implements Runnable
{
    private Lock lock;
    private CountDownLatch countDownLatch;
    private boolean needTry;

    public ReentrantLockTryCallable(Lock lock, CountDownLatch countDownLatch, boolean needTry)
    {
        this.lock = lock;
        this.countDownLatch = countDownLatch;
        this.needTry = needTry;
    }

    @Override
    public void run()
    {
        boolean getLock = true;
        try
        {
            countDownLatch.await();
            if(needTry)
                getLock = lock.tryLock(30, TimeUnit.SECONDS);
            else
                lock.lock();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            if(!needTry || getLock)
                lock.unlock();
        }
    }
}
