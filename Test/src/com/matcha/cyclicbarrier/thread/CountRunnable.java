package com.matcha.cyclicbarrier.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2016/8/8.
 */
public class CountRunnable implements Runnable
{
    private CyclicBarrier cyclicBarrier;
    private int start;
    private volatile int sum;

    public CountRunnable(CyclicBarrier cyclicBarrier, int start)
    {
        this.cyclicBarrier = cyclicBarrier;
        this.start = start;
        this.sum = 0;
    }

    @Override
    public void run()
    {
        try
        {
            for(int i = 0;i < 5;i++)
                sum += (start + i);
            cyclicBarrier.await();
            Thread localThread = Thread.currentThread();
            System.out.println("CountThread " + localThread.getName() + " is finish!");
        }
        catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getSum()
    {
        return sum;
    }
}
