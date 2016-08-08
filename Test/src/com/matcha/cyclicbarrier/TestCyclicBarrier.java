package com.matcha.cyclicbarrier;

import com.matcha.cyclicbarrier.app.CountHelper;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TestCyclicBarrier
{
    public static void main(String[] args)
    {
        CountHelper countHelper = new CountHelper(10, 20);
        int sum = countHelper.count();
        System.out.println("sum is " + sum);
    }
}
