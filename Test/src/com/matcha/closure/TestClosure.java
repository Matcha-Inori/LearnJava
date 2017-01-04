package com.matcha.closure;

import com.matcha.atomic.app.People;
import com.matcha.atomic.data.RandomPeople;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TestClosure
{
    public static void main(String[] args)
    {
        People oldPeople = RandomPeople.randomPeople();
        People newPeople1 = RandomPeople.randomPeople();
        People newPeople2 = RandomPeople.randomPeople();
        AtomicStampedReference<People> peopleAtomicStampedReference =
                new AtomicStampedReference<>(oldPeople, 0);
        ISetter iSetter1 = new ISetter()
        {
            @Override
            public People setter(AtomicStampedReference<People> oldPeopleReference)
            {
                int[] oldStamp = new int[1];
                People thePeople = oldPeopleReference.get(oldStamp);
                while(!oldPeopleReference.compareAndSet(oldPeople, newPeople1, oldStamp[0], oldStamp[0]++))
                    thePeople = oldPeopleReference.get(oldStamp);
                return thePeople;
            }
        };

        ISetter iSetter2 = new ISetter()
        {
            @Override
            public People setter(AtomicStampedReference<People> oldPeopleReference)
            {
                boolean successful = false;
                int[] oldStamp = new int[1];
                People thePeople = oldPeopleReference.get(oldStamp);
                while(!oldPeopleReference.compareAndSet(newPeople1, newPeople2, oldStamp[0], oldStamp[0]++))
                    thePeople = oldPeopleReference.get(oldStamp);
                successful = true;
                return thePeople;
            }
        };
        System.out.println("oldPeople : " + oldPeople);
        System.out.println("newPeople1 : " + newPeople1);
        System.out.println("newPeople2 : " + newPeople2);
        System.out.println("peopleAtomicStampedReference : " + peopleAtomicStampedReference.getReference());
        iSetter1.setter(peopleAtomicStampedReference);
        System.out.println("peopleAtomicStampedReference : " + peopleAtomicStampedReference.getReference());
        iSetter2.setter(peopleAtomicStampedReference);
        System.out.println("peopleAtomicStampedReference : " + peopleAtomicStampedReference.getReference());
    }
}
