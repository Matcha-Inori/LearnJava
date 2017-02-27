package com.matcha.threadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/2/27.
 */
public class CallableThreadTest
{
    public static void main(String[] args)
    {
        try
        {
            List<Future<Integer>> futureList = new ArrayList<>();
            ExecutorService threadPool = Executors.newFixedThreadPool(3);
            for(int count = 0;count < 5;count++)
                futureList.add(threadPool.submit(new OneCallable(count + 1)));
            threadPool.shutdownNow();
            for(Future<Integer> future : futureList)
                future.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    private static class OneCallable implements Callable<Integer>
    {
        private int result;

        public OneCallable(int result)
        {
            this.result = result;
        }

        @Override
        public Integer call() throws Exception
        {
            return result;
        }
    }
}
