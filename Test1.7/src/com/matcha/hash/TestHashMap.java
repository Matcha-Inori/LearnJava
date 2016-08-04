package com.matcha.hash;

import com.matcha.hash.app.TestHashObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rd_xidong_ren on 2016/7/27.
 */
public class TestHashMap
{
    public static void main(String[] args)
    {
        Map map = new HashMap();
        TestHashObject testObj1 = new TestHashObject(30, "abc");
        TestHashObject testObj2 = new TestHashObject(30, "abc");
        map.put(testObj1, 1);
        System.out.println(map.get(testObj2));
    }
}
