package com.matcha.lock.factory;

import com.matcha.lock.thread.ReentrantLockTryCallable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rd_xidong_ren on 2016/7/21.
 */
public class DefaultThreadFacotry implements IThreadFactory
{
    private volatile AtomicInteger threadIndex;
    private static volatile IThreadFactory instance = null;
    private Lock lock;
    private volatile CountDownLatch countDownLatch;
    private volatile boolean flag;

    private DefaultThreadFacotry()
    {
        threadIndex = new AtomicInteger(0);
        lock = new ReentrantLock(true);
        countDownLatch = new CountDownLatch(1);
        flag = true;
    }

    public static IThreadFactory getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new DefaultThreadFacotry();
    }

    @Override
    public Thread newThread()
    {
        if(countDownLatch.getCount() == 0)
            countDownLatch = new CountDownLatch(1);
        int threadIndex = this.threadIndex.getAndIncrement();
        Thread thread = new Thread(new ReentrantLockTryCallable(lock, countDownLatch, needTry(flag, threadIndex)));
        thread.setName("MatchaThread - " + threadIndex);
        return thread;
    }

    private boolean needTry(boolean flag, int index)
    {
        return flag ? index % 2 == 0 : index % 2 != 0;
    }

    @Override
    public void startAllThread()
    {
        countDownLatch.countDown();
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
}
