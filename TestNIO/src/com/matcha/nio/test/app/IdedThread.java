package com.matcha.nio.test.app;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/9/20.
 */
public class IdedThread extends Thread
{
    private static final AtomicInteger nextId;

    static
    {
        nextId = new AtomicInteger(0);
    }

    protected int id;

    public IdedThread()
    {
        id = nextId();
    }

    private int nextId()
    {
        return nextId.getAndIncrement();
    }
}
