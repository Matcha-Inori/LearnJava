package com.matcha.forkjoin.task;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 16/8/7.
 */
public class CountTask extends RecursiveTask<Integer>
{
    private static final int THERE_SHOULD;
    private static final AtomicInteger nextNumber;

    static
    {
        THERE_SHOULD = 2;
        nextNumber = new AtomicInteger(0);
    }

    private int start;
    private int end;
    private volatile int number;

    public CountTask(int start, int stop)
    {
        this.start = start;
        this.end = stop;
        this.number = nextNumber.getAndIncrement();
    }

    @Override
    protected Integer compute()
    {
        int sum = 0;
        boolean canCompute = (end - start) <= THERE_SHOULD;
        if(canCompute)
        {
            for(int i = start;i <= end;i++)
                sum += i;
        }
        else
        {
            int middle = (start + end) / 2;
            RecursiveTask<Integer> recursiveTaskOne = new CountTask(start, middle);
            RecursiveTask<Integer> recursiveTaskTwo = new CountTask(middle + 1, end);
            recursiveTaskOne.fork();
            recursiveTaskTwo.fork();
            int resultOne = recursiveTaskOne.join();
            int resultTwo = recursiveTaskTwo.join();
            sum = resultOne + resultTwo;
        }
        return sum;
    }
}
