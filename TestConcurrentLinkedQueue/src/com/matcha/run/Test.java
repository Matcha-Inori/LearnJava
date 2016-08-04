package com.matcha.run;

import com.matcha.app.People;
import com.matcha.thread.OfferRunnable;
import com.matcha.thread.PollRunnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 16/8/1.
 */
public class Test
{
    public static void main(String[] args)
    {
        Queue<People> peopleQueue = new ConcurrentLinkedQueue<>();
        int maxPeopleCount = 30;
        CountDownLatch countDownLatch = new CountDownLatch(maxPeopleCount);
        AtomicInteger offeredPeopleCount = new AtomicInteger(0);
        Thread offerThread1 = new Thread(new OfferRunnable(peopleQueue, offeredPeopleCount, maxPeopleCount),
                "OfferThread - 1");
        Thread offerThread2 = new Thread(new OfferRunnable(peopleQueue, offeredPeopleCount, maxPeopleCount),
                "OfferThread - 2");
        Thread offerThread3 = new Thread(new OfferRunnable(peopleQueue, offeredPeopleCount, maxPeopleCount),
                "OfferThread - 3");
        PollRunnable pollRunnable1 = new PollRunnable(peopleQueue, countDownLatch);
        PollRunnable pollRunnable2 = new PollRunnable(peopleQueue, countDownLatch);
        Thread pollThread1 = new Thread(pollRunnable1, "pollThread - 1");
        Thread pollThread2 = new Thread(pollRunnable2, "pollThread - 2");

        offerThread1.start();
        offerThread2.start();
        offerThread3.start();
        pollThread1.start();
        pollThread2.start();

        try
        {
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        pollRunnable1.setStop(true);
        pollRunnable2.setStop(true);
    }
}
