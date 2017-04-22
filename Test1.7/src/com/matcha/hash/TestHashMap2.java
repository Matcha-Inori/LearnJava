package com.matcha.hash;

import com.matcha.hash.app.TestHashObject;
import com.matcha.hash.app.TestHashObject2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/21.
 */
public class TestHashMap2
{
    private static final TestHashObject2[] testHashObjects;

    static
    {
        testHashObjects = new TestHashObject2[]{
                new TestHashObject2(10, "A"),
                new TestHashObject2(20, "B"),
                new TestHashObject2(30, "C"),
                new TestHashObject2(40, "D"),
                new TestHashObject2(50, "E"),
                new TestHashObject2(60, "F"),
                new TestHashObject2(70, "G"),
                new TestHashObject2(80, "H"),
                new TestHashObject2(90, "I"),
                new TestHashObject2(100, "J"),
        };
    }

    public static void main(String[] args)
    {
        HashMap<TestHashObject2, Integer> hashMap = new HashMap<>();
        List<TestHashObject2> testHashObject2List = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        TestHashObject2 testHashObject2;
        for(int index = 0;index < testHashObjects.length;index++)
            hashMap.put(testHashObjects[index], random.nextInt());
        for(int index = 0;index < 100;index++)
        {
            testHashObject2 = testHashObjects[random.nextInt(testHashObjects.length)];
            testHashObject2 = testHashObject2.clone();
            hashMap.put(testHashObject2, random.nextInt());
            if(random.nextBoolean())
                testHashObject2List.add(testHashObject2);
        }
        for(int index = 0;index < testHashObjects.length;index++)
            System.out.println(testHashObjects[index].toString() + hashMap.get(testHashObjects[index]));
        for(TestHashObject2 oneOfList : testHashObject2List)
            System.out.println(oneOfList.toString() + hashMap.get(oneOfList));
    }
}
