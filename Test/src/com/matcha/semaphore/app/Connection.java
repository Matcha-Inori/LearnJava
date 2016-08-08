package com.matcha.semaphore.app;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/8/8.
 */
public class Connection
{
    private static AtomicInteger nextNumber;

    static
    {
        nextNumber = new AtomicInteger(0);
    }

    private int number;

    public Connection()
    {
        number = nextNumber.getAndIncrement();
    }

    @Override
    public String toString()
    {
        return "Connection{" + "number=" + number + '}';
    }
}
