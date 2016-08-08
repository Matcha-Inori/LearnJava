package com.matcha.callable.thread;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/8/8.
 */
public class CountCallable implements Callable<Integer>
{
    private int start;

    public CountCallable(int start)
    {
        this.start = start;
    }

    @Override
    public Integer call() throws Exception
    {
        Thread.sleep(3000);
        int sum = 0;
        for(int i = 0;i < start;i++)
            sum += (this.start + i);
        return sum;
    }
}
