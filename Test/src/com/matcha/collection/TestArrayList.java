package com.matcha.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matcha on 2017/7/31.
 */
public class TestArrayList
{
    public static void main(String[] args)
    {
        List<Integer> arrayList = new ArrayList<>(5);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        System.out.println(arrayList);
    }
}
