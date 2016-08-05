package com.matcha.run;

import com.matcha.app.People;
import com.matcha.thread.OfferIntoDequeRunnable;
import com.matcha.thread.PollFromDequeRunnable;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/8/5.
 */
public class TestConcurrentLinkedDeque
{
    public static void main(String[] args)
    {
        Deque<People> peopleDeque = new ConcurrentLinkedDeque<>();
        AtomicInteger count = new AtomicInteger(0);
        int maxCount = 30;
        CountDownLatch countDownLatch = new CountDownLatch(maxCount);
        Thread offerIntoHeadThread = new Thread(new OfferIntoDequeRunnable(peopleDeque, count, maxCount, true),
                "offer-head-thread");
        Thread offerIntoTailThread = new Thread(new OfferIntoDequeRunnable(peopleDeque, count, maxCount, false),
                "offer-tail-thread");
        PollFromDequeRunnable pollFromHeadRunnable = new PollFromDequeRunnable(peopleDeque, countDownLatch, true);
        PollFromDequeRunnable pollFromTailRunnable = new PollFromDequeRunnable(peopleDeque, countDownLatch, false);
        Thread pollFromHeadThread = new Thread(pollFromHeadRunnable, "poll-head-thread");
        Thread pollFromTailThread = new Thread(pollFromTailRunnable, "poll-tail-thread");
        offerIntoHeadThread.start();
        offerIntoTailThread.start();
        pollFromHeadThread.start();
        pollFromTailThread.start();

        try
        {
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        pollFromHeadRunnable.setStop(true);
        pollFromTailRunnable.setStop(true);
    }
}
