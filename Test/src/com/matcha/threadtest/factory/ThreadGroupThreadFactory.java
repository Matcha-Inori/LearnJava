package com.matcha.threadtest.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/4/27.
 */
public class ThreadGroupThreadFactory implements ThreadFactory
{
    private static final AtomicLong nextId;
    private static final AtomicLong nextThreadId;
    private static final AtomicLong nextThreadGroupId;
    private static final String threadPrefix;
    private static final String threadGroupPrefix;

    static
    {
        nextId = new AtomicLong(0);
        nextThreadId = new AtomicLong(0);
        nextThreadGroupId = new AtomicLong(0);
        threadPrefix = "MyThread-";
        threadGroupPrefix = "MyThreadGroup-";
    }

    private long id;
    private ThreadGroup firstThreadGroup;
    private ThreadGroup secondThreadGroup;

    public ThreadGroupThreadFactory()
    {
        id = nextId.getAndIncrement();
        createNewThreadGroup();
    }

    private void createNewThreadGroup()
    {
        firstThreadGroup = new ThreadGroup(genName(threadGroupPrefix, nextThreadGroupId.getAndIncrement()));
        secondThreadGroup = new ThreadGroup(
                firstThreadGroup,
                genName(threadGroupPrefix, nextThreadGroupId.getAndIncrement())
        );
    }

    private String genName(String prefix, long id)
    {
        return String.format("%s%d", prefix, id);
    }

    @Override
    public Thread newThread(Runnable runnable)
    {
        long threadId = nextThreadId.getAndIncrement();
        ThreadGroup threadGroup = threadId % 2 == 0 ? firstThreadGroup : secondThreadGroup;
        Thread thread = new Thread(threadGroup, runnable, genName(threadPrefix, threadId));
        return thread;
    }

    public void waitOnThreadGroup(boolean firstGroup) throws InterruptedException
    {
        if(firstGroup)
            waitOnThreadGroup(this.firstThreadGroup);
        else
            waitOnThreadGroup(this.secondThreadGroup);
    }

    private void waitOnThreadGroup(ThreadGroup threadGroup) throws InterruptedException
    {
        synchronized (threadGroup)
        {
            if(threadGroup.activeCount() > 0)
                threadGroup.wait();
        }
    }

    public void destroyThreadGroup()
    {
        destroyThreadGroup(this.secondThreadGroup);
        destroyThreadGroup(this.firstThreadGroup);
        createNewThreadGroup();
    }

    private void destroyThreadGroup(ThreadGroup threadGroup)
    {
        synchronized (threadGroup)
        {
            while(threadGroup.activeCount() > 0)
            {
                try
                {
                    threadGroup.wait();
                }
                catch (InterruptedException e)
                {
                    Thread.interrupted();
                }
            }
            threadGroup.destroy();
        }
    }
}