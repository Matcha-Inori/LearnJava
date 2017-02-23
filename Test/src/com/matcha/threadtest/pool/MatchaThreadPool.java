package com.matcha.threadtest.pool;

import java.util.concurrent.*;

/**
 * Created by Matcha on 2017/2/20.
 */
public class MatchaThreadPool
{
    private BlockingQueue<Job> jobQueue;
    private Thread[] threads;

    private MatchaThreadPool()
    {
        jobQueue = new LinkedBlockingQueue<>(5);
        threads = new Thread[]{
                new Thread(new Worker()),
                new Thread(new Worker()),
                new Thread(new Worker()),
                new Thread(new Worker()),
                new Thread(new Worker())
        };
        for(Thread thread : threads)
            thread.start();
    }

    public void submit(Runnable runnable)
    {
        jobQueue.add(new Job(runnable));
    }

    public <T> Future<T> submit(Callable<T> callable)
    {
        FutureTask<T> futureTask = new FutureTask<T>(callable);
        this.submit(futureTask);
        return futureTask;
    }

    private class Worker extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                Job job;
                Runnable runnable;
                while(true)
                {
                    job = jobQueue.take();
                    runnable = job.getRunnable();
                    runnable.run();
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
