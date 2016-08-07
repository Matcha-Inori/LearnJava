package com.matcha.forkjoin;

import com.matcha.forkjoin.task.CountTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by Matcha on 16/8/7.
 */
public class TestForkJoin
{
    public static void main(String[] args)
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1, 1000);
        Future<Integer> resultFuture = forkJoinPool.submit(countTask);
        int result = 0;
        try
        {
            result = resultFuture.get();
            System.out.println(result);
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
