package com.matcha.server.thread;

import com.matcha.app.People;
import com.matcha.data.RandomPeople;

import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 16/8/5.
 */
public class OfferIntoDequeRunnable implements Runnable
{
    private Deque<People> peopleDeque;
    private AtomicInteger count;
    private int maxCount;
    private boolean addIntoHead;

    public OfferIntoDequeRunnable(Deque<People> peopleDeque, AtomicInteger count, int maxCount, boolean addIntoHead)
    {
        this.peopleDeque = peopleDeque;
        this.count = count;
        this.maxCount = maxCount;
        this.addIntoHead = addIntoHead;
    }

    @Override
    public void run()
    {
        People people = null;
        while(count.incrementAndGet() <= maxCount)
        {
            people = RandomPeople.randomPeople();
            if(addIntoHead)
                peopleDeque.offerFirst(people);
            else
                peopleDeque.offerLast(people);
        }
    }
}
