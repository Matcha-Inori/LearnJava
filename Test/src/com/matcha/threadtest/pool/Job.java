package com.matcha.threadtest.pool;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Matcha on 2017/2/20.
 */
public class Job
{
    private static AtomicLong nextId;

    static
    {
        nextId = new AtomicLong(0);
    }

    private Runnable runnable;
    private long id;

    public Job(Runnable runnable)
    {
        this.runnable = runnable;
        this.id = nextId.getAndIncrement();
    }

    public Runnable getRunnable()
    {
        return runnable;
    }
}
