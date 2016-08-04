package com.matcha.concurrenthash;

import com.matcha.hash.app.TestHashObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rd_xidong_ren on 2016/7/27.
 */
public class TestConcurrentHashMap
{
    public static void main(String[] args)
    {
        Map map = new ConcurrentHashMap();
        TestHashObject testObj1 = new TestHashObject(30, "abc");
        TestHashObject testObj2 = new TestHashObject(30, "abc");
        map.put(testObj1, 1);
        System.out.println(map.get(testObj2));
    }
}
