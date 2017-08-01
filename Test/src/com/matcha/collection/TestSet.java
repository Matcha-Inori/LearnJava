package com.matcha.collection;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matcha on 2017/7/31.
 */
public class TestSet
{
    public static void main(String[] args)
    {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("A");
        hashSet.add("B");
        hashSet.add("C");

        hashSet.add("A");

        hashSet.contains("A");

        System.out.println(hashSet);
    }
}
