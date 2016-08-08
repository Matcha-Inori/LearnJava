package com.matcha.cyclicbarrier.app;

import com.matcha.cyclicbarrier.thread.CountRunnable;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by Administrator on 2016/8/8.
 */
public class CountHelper
{
    private int startA;
    private int startB;

    public CountHelper(int startA, int startB)
    {
        this.startA = startA;
        this.startB = startB;
    }

    public int count()
    {
        try
        {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            CountRunnable[] countRunnables = new CountRunnable[2];
            MergeRunnable mergeRunnable = new MergeRunnable(countRunnables, countDownLatch);
            CyclicBarrier cyclicBarrier = new CyclicBarrier(2, mergeRunnable);
            countRunnables[0] = new CountRunnable(cyclicBarrier, startA);
            countRunnables[1] = new CountRunnable(cyclicBarrier, startB);
            Thread countThread1 = new Thread(countRunnables[0], "Matcha-CountThread-1");
            Thread countThread2= new Thread(countRunnables[1], "Matcha-CountThread-2");
            countThread1.start();
            countThread2.start();
            countDownLatch.await();
            return mergeRunnable.sum;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private class MergeRunnable implements Runnable
    {
        private CountRunnable[] countRunnables;
        private volatile int sum;
        private CountDownLatch countDownLatch;

        public MergeRunnable(CountRunnable[] countRunnables, CountDownLatch countDownLatch)
        {
            this.countRunnables = countRunnables;
            this.countDownLatch = countDownLatch;
            this.sum = 0;
        }

        @Override
        public void run()
        {
            int localSum = this.sum;
            for(CountRunnable countRunnable : countRunnables)
                localSum += countRunnable.getSum();
            this.sum = localSum;
            this.countDownLatch.countDown();
            Thread localThread = Thread.currentThread();
            System.out.println("MergeThread " + localThread.getName() + " is finish!");
        }
    }
}
