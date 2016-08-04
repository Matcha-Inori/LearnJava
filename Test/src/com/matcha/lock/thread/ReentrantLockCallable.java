package com.matcha.lock.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * Created by rd_xidong_ren on 2016/7/19.
 */
public class ReentrantLockCallable implements Runnable
{
    private Lock lock;
    private CountDownLatch countDownLatch;

    public ReentrantLockCallable(Lock lock, CountDownLatch countDownLatch) {
        this.lock = lock;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        try
        {
            countDownLatch.await();
            System.out.println("lock!!!! " + currentThread.getName());
            lock.lock();
            System.out.println("getLock!!!! " + currentThread.getName());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            System.out.println("unlock!!!! " + currentThread.getName());
            lock.unlock();
            System.out.println("already unlock!!!! " + currentThread.getName());
        }
    }
}
