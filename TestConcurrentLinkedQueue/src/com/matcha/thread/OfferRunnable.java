package com.matcha.thread;

import com.matcha.app.People;
import com.matcha.data.RandomPeople;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 16/8/1.
 */
public class OfferRunnable implements Runnable
{
    private Queue<People> peopleQueue;
    private AtomicInteger count;
    private int maxCount;

    public OfferRunnable(Queue<People> peopleQueue, AtomicInteger count, int maxCount)
    {
        this.peopleQueue = peopleQueue;
        this.count = count;
        this.maxCount = maxCount;
    }

    @Override
    public void run()
    {
        People people = null;
        while(count.incrementAndGet() <= maxCount)
        {
            people = RandomPeople.randomPeople();
            peopleQueue.offer(people);
        }
    }
}
