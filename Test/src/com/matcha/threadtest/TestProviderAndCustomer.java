package com.matcha.threadtest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/7/19.
 */
public class TestProviderAndCustomer
{
    public static void main(String[] args)
    {
        try
        {
            Future<Integer>[] futures = new Future[3];
            BlockingQueue<Integer>[] blockingQueues = new BlockingQueue[3];
            MyThreadFactory threadFactory = new MyThreadFactory("My");
            ExecutorService executorService = Executors.newFixedThreadPool(3, threadFactory);
            BlockingQueue<Integer> queue;
            for(int index = 0;index < 3;index++)
            {
                blockingQueues[index] = queue = new LinkedBlockingQueue<>();
                futures[index] = executorService.submit(new Customer(queue));
            }
            int customerIndex;
            for(int index = 0;index < 300;index++)
            {
                customerIndex = index % 3;
                queue = blockingQueues[customerIndex];
                queue.offer(index + 1);
            }
            executorService.shutdown();
            threadFactory.destroyAllThread();
            int result = 0;
            int subResult;
            for(int index = 0;index < 3;index++)
            {
                subResult = futures[index].get();
                result += subResult;
            }
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

class MyThreadFactory implements ThreadFactory
{
    private ThreadGroup threadGroup;
    private String threadGroupName;
    private AtomicLong threadCount;

    public MyThreadFactory(String threadGroupName)
    {
        this.threadGroupName = threadGroupName;
        this.threadGroup = new ThreadGroup(threadGroupName);
        this.threadCount = new AtomicLong(0);
    }

    @Override
    public Thread newThread(Runnable r)
    {
        String threadName = String.format("Customer - " + this.threadCount.getAndIncrement());
        Thread thread = new Thread(this.threadGroup, r, threadName);
        return thread;
    }

    public void destroyAllThread() throws InterruptedException
    {
        try
        {
            synchronized (threadGroup)
            {
                System.out.println(threadGroup);
                threadGroup.interrupt();
                threadGroup.wait();
                threadGroup.destroy();
            }
        }
        catch(InterruptedException exception)
        {
            exception.printStackTrace();
        }
    }
}

class Customer implements Callable<Integer>
{
    private BlockingQueue<Integer> queue;

    public Customer(BlockingQueue<Integer> queue)
    {
        this.queue = queue;
    }

    @Override
    public Integer call() throws Exception
    {
        boolean finish = false;
        int mission;
        int result = 0;
        while(true)
        {
            if(finish && this.queue.isEmpty()) break;
            try
            {
                mission = queue.take();
                result = result + mission;
            }
            catch (InterruptedException exception)
            {
                finish = true;
            }
        }
        return result;
    }
}
