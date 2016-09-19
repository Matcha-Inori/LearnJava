package com.matcha.server.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 16/8/8.
 */
public class SimulateThreadFactory implements ThreadFactory
{
    private static AtomicInteger nextNumber;

    static
    {
        nextNumber = new AtomicInteger(0);
    }

    @Override
    public Thread newThread(Runnable r)
    {
        Thread newThread = new Thread(r, "Simulate-Thread-" + nextNumber());
        return newThread;
    }

    private int nextNumber()
    {
        return nextNumber.getAndIncrement();
    }
}
