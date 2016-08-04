package com.matcha.thread;

import com.matcha.app.People;

import java.util.Deque;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Matcha on 16/8/5.
 */
public class PollFromDequeRunnable implements Runnable
{
    private Deque<People> peopleDeque;
    private CountDownLatch countDownLatch;
    private boolean pollFromHead;
    private volatile boolean stop;

    public PollFromDequeRunnable(Deque<People> peopleDeque, CountDownLatch countDownLatch, boolean pollFromHead)
    {
        this.peopleDeque = peopleDeque;
        this.countDownLatch = countDownLatch;
        this.pollFromHead = pollFromHead;
        this.stop = false;
    }

    @Override
    public void run()
    {
        try
        {
            People nextPeople = null;
            while(!stop)
            {
                nextPeople = (pollFromHead) ? peopleDeque.pollFirst() : peopleDeque.pollLast();
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

    public boolean isStop()
    {
        return stop;
    }

    public void setStop(boolean stop)
    {
        this.stop = stop;
    }
}
