package com.matcha.atomic;

import com.matcha.atomic.app.People;
import com.matcha.atomic.data.RandomPeople;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TestAtomicReferenceFieldUpdater
{
    public static void main(String[] args)
    {
        //要修改的成员变量必须是public volatile的
//        AtomicReferenceFieldUpdater<People, String> peopleStringAtomicReferenceFieldUpdater =
//                AtomicReferenceFieldUpdater.newUpdater(People.class, String.class, "name");
        AtomicReferenceFieldUpdater<People, String> peopleStringAtomicReferenceFieldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(People.class, String.class, "model");
        People people = RandomPeople.randomPeople();
        People otherPeople = RandomPeople.randomPeople();
        boolean isSuccess1 =
                peopleStringAtomicReferenceFieldUpdater.compareAndSet(people, people.model, "AAA");
        boolean isSuccess2 =
                peopleStringAtomicReferenceFieldUpdater.compareAndSet(otherPeople, otherPeople.model, "BBB");
        System.out.println("isSuccess1 " + isSuccess1 + " and name is " + people.model);
        System.out.println("isSuccess2 " + isSuccess2 + " and name is " + otherPeople.model);
    }
}
