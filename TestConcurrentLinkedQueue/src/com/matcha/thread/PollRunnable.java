package com.matcha.thread;

import com.matcha.app.People;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Matcha on 16/8/1.
 */
public class PollRunnable implements Runnable
{
    private Queue<People> peopleQueue;
    private volatile boolean stop;
    private CountDownLatch countDownLatch;

    public PollRunnable(Queue<People> peopleQueue, CountDownLatch countDownLatch)
    {
        this.peopleQueue = peopleQueue;
        this.countDownLatch = countDownLatch;
        stop = false;
    }

    @Override
    public void run()
    {
        try
        {
            People nextPeople = null;
            while(!stop)
            {
                nextPeople = peopleQueue.poll();
                if(nextPeople == null)
                {
                    Thread.sleep(100);
                    continue;
                }
                countDownLatch.countDown();
                System.out.println("next people is " + nextPeople);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setStop(boolean stop)
    {
        this.stop = stop;
    }

    public boolean isStop()
    {
        return stop;
    }
}
