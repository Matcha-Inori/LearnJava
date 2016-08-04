package com.matcha.lock.factory;

import com.matcha.lock.app.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rd_xidong_ren on 2016/7/25.
 */
public class ReadWriteThreadFacotry implements IReadWriteThreadFacotry
{
    private static volatile IReadWriteThreadFacotry instance;
    private static Map<Integer, String> data;
    private static final String getThreadFormatter;
    private static final String putThreadFormatter;

    static
    {
        instance = null;
        getThreadFormatter = "Matcha_Get_Thread - %d";
        putThreadFormatter = "Matcha_Put_Thread - %d";
        data = new HashMap<Integer, String>();
        data.put(1, "Matcha");
        data.put(2, "Randone");
        data.put(3, "Dell");
        data.put(4, "Ice");
        data.put(5, "LiSA");
        data.put(6, "Phone");
    }

    private final AtomicInteger getThreadNumber;
    private final AtomicInteger putThreadNumber;
    private volatile CountDownLatch countDownLatch;

    private ReadWriteThreadFacotry()
    {
        getThreadNumber = new AtomicInteger(0);
        putThreadNumber = new AtomicInteger(0);
        countDownLatch = new CountDownLatch(1);
    }

    public static IReadWriteThreadFacotry getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    public static synchronized void createInstance()
    {
        if(instance == null)
            instance = new ReadWriteThreadFacotry();
    }

    @Override
    public void startAllThread()
    {
        countDownLatch.countDown();
        countDownLatch = new CountDownLatch(1);
    }

    @Override
    public Thread newGetThread()
    {
        Thread getThread = new Thread(new GetRunnable());
        getThread.setName(String.format(getThreadFormatter,
                getThreadNumber.getAndIncrement()));
        getThread.start();
        return getThread;
    }

    @Override
    public Thread newPutThread()
    {
        Thread putThread = new Thread(new PutRunnable());
        putThread.setName(String.format(putThreadFormatter,
                putThreadNumber.getAndIncrement()));
        putThread.start();
        return putThread;
    }

    private class GetRunnable implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                countDownLatch.await();
                Random random = new Random();
                int size = data.size();
                int key1 = random.nextInt(size);
                int key2 = random.nextInt(size);
                int key3 = random.nextInt(size);
                Cache cache = Cache.getInstance();
                String val1 = cache.get(key1);
                System.out.println("val1 = " + val1);
                String val2 = cache.get(key2);
                System.out.println("val2 = " + val2);
                String val3 = cache.get(key3);
                System.out.println("val3 = " + val3);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private class PutRunnable implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                countDownLatch.await();
                Cache cache = Cache.getInstance();
                Random random = new Random();
                int size = data.size();
                int key1 = random.nextInt(size);
                int key2 = random.nextInt(size);
                int key3 = random.nextInt(size);
                cache.set(key1, data.get(key1));
                cache.set(key1, data.get(key2));
                cache.set(key1, data.get(key3));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
