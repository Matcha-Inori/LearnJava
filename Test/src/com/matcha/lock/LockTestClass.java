package com.matcha.lock;

import com.matcha.lock.thread.ReentrantLockCallable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rd_xidong_ren on 2016/7/19.
 */
public class LockTestClass {
    public static void main(String[] args) {
        try
        {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Lock lock = new ReentrantLock(true);
            Thread thread1 = new Thread(new ReentrantLockCallable(lock, countDownLatch));
            Thread thread2 = new Thread(new ReentrantLockCallable(lock, countDownLatch));
            thread1.start();
            thread2.start();
            Thread.sleep(10);
            countDownLatch.countDown();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
