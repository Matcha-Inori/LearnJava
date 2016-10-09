package com.matcha.io.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */
public class ReadTestResult
{
    private String testName;
    private List<Long> timeList;

    public ReadTestResult(String testName)
    {
        this.testName = testName;
        this.timeList = new ArrayList<>(3);
    }

    public void addTime(long time)
    {
        timeList.add(time);
    }

    public void printResult()
    {
        System.out.println("\ntest - " + testName + " : ");
        Long time;
        long allTime = 0;
        for(int i = 0;i < timeList.size();i++)
        {
            time = timeList.get(i);
            allTime += time;
            System.out.println("the " + (i + 1) + "th test cost - " + time);
        }
        long average = allTime / timeList.size();
        System.out.println("all cost " + allTime);
        System.out.println("average cost " + average);
    }
}
