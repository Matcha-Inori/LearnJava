package com.matcha.atomic;

import com.matcha.atomic.app.People;
import com.matcha.atomic.data.RandomPeople;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TestAtomicStampedReference
{
    public static void main(String[] args)
    {
        People oldPeople = RandomPeople.randomPeople();
        AtomicStampedReference<People> peopleAtomicStampedReference =
                new AtomicStampedReference<>(oldPeople, 1);
        People newPeople = RandomPeople.randomPeople();
        People otherPeople = RandomPeople.randomPeople();
        int stamp = peopleAtomicStampedReference.getStamp();
        boolean isSuccess1 = peopleAtomicStampedReference.compareAndSet(oldPeople, newPeople, stamp, ++stamp);
        boolean isSuccess2 = peopleAtomicStampedReference.compareAndSet(newPeople, oldPeople, stamp, ++stamp);
        boolean isSuccess3 = peopleAtomicStampedReference.compareAndSet(oldPeople, otherPeople, stamp - 2, ++stamp);
        System.out.println("isSuccess1 " + isSuccess1);
        System.out.println("isSuccess2 " + isSuccess2);
        System.out.println("isSuccess3 " + isSuccess3);
    }
}
