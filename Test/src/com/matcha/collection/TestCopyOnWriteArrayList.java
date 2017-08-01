package com.matcha.collection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Matcha on 2017/8/1.
 */
public class TestCopyOnWriteArrayList
{
    public static void main(String[] args)
    {
        List<Long> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        Set<Long> longSet = new HashSet<>();
        longSet.add(1L);
        longSet.add(2L);
        longSet.add(3L);
        copyOnWriteArrayList.add(4L);
        copyOnWriteArrayList.addAll(longSet);
    }
}
