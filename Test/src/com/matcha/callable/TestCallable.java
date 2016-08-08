package com.matcha.callable;

import com.matcha.callable.thread.CountCallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TestCallable
{
    public static void main(String[] args)
    {
        try
        {
            Callable<Integer> countCallable = new CountCallable(10);
            FutureTask<Integer> countFutureTask = new FutureTask<Integer>(countCallable);
            Thread countThread = new Thread(countFutureTask, "Count-Thread");
            countThread.start();
            System.out.println("to get the sum!");
            int sum = countFutureTask.get();
            System.out.println("sum is " + sum);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
