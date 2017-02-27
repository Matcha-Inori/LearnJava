package com.matcha.threadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/2/27.
 */
public class ScheduledThreadPoolTest
{
    public static void main(String[] args)
    {
        try
        {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
            List<Future<?>> futureList1 = new ArrayList<>();
            List<Future<Integer>> futureList2 = new ArrayList<>();
            for(int count = 0;count < 5;count++)
                futureList1.add(executorService.scheduleAtFixedRate(
                        new OneRunnable(),
                        10,
                        30,
                        TimeUnit.SECONDS

                ));
            for(int count = 0;count < 5;count++)
                futureList2.add(executorService.schedule(new OneCallable(count + 1), 10, TimeUnit.SECONDS));

            executorService.shutdown();

            for(Future<?> future : futureList1)
                future.get();
            for(Future<Integer> future : futureList2)
                future.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
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

    private static class OneRunnable implements Runnable
    {
        private static final AtomicLong nextId;

        static
        {
            nextId = new AtomicLong(0);
        }

        private long id;

        public OneRunnable()
        {
            id = nextId.getAndIncrement();
        }

        @Override
        public void run()
        {
            System.out.println(id);
        }
    }
}
