package com.matcha.client.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SimulateClientThreadFacotry implements ThreadFactory
{
    private static AtomicInteger nextNumber;

    static
    {
        nextNumber = new AtomicInteger(0);
    }

    @Override
    public Thread newThread(Runnable r)
    {
        Thread newThread = new Thread(r, "Simulate-Client-Thread-" + nextNumber());
        return newThread;
    }

    private int nextNumber()
    {
        return nextNumber.getAndIncrement();
    }
}
