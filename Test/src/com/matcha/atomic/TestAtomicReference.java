package com.matcha.atomic;

import com.matcha.atomic.app.People;
import com.matcha.atomic.data.RandomPeople;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TestAtomicReference
{
    public static void main(String[] args)
    {
        try
        {
            People oldPeople = RandomPeople.randomPeople();
            AtomicReference<People> peopleAtomicReference = new AtomicReference<>(oldPeople);
            People newPeople = RandomPeople.randomPeople();
            boolean isSuccess = peopleAtomicReference.compareAndSet(oldPeople.clonePeople(), newPeople);
            boolean isNewPeople = peopleAtomicReference.get() == newPeople;
            boolean isOldPeople = peopleAtomicReference.get() == oldPeople;
            System.out.println("isSuccess " + String.valueOf(isSuccess));
            System.out.println("isNewPeople " + String.valueOf(isNewPeople));
            System.out.println("isOldPeople " + String.valueOf(isOldPeople));

            System.out.println("==========================================");

            isSuccess = peopleAtomicReference.compareAndSet(oldPeople, newPeople);
            isNewPeople = peopleAtomicReference.get() == newPeople;
            isOldPeople = peopleAtomicReference.get() == oldPeople;
            System.out.println("isSuccess " + String.valueOf(isSuccess));
            System.out.println("isNewPeople " + String.valueOf(isNewPeople));
            System.out.println("isOldPeople " + String.valueOf(isOldPeople));
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
