package com.matcha.threadtest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/4/27.
 */
public class ThreadGroupTest
{
    public static void main(String[] args)
    {
        ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        int threadCount = 5;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler();
        Thread thread;
        for(int index = 0;index < threadCount;index++)
        {
            thread = new Thread(threadGroup, new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread currentThread = Thread.currentThread();
                        countDownLatch.await();
                        System.out.println(currentThread.getName() + " is finish!");
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            thread.start();
        }
        System.out.println("threadGroup.activeCount() = " + threadGroup.activeCount());
        threadGroup.list();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread oneOfThreads : threads)
            System.out.println(String.format("Thread %s: %s\n", oneOfThreads.getName(), oneOfThreads.getState()));
        threadGroup.interrupt();
        countDownLatch.countDown();
    }

    private static class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler
    {
        @Override
        public void uncaughtException(Thread t, Throwable e)
        {
            if(RuntimeException.class.isInstance(e) && e.getCause() == null) e.getCause().printStackTrace();
            e.printStackTrace();
        }
    }
}
