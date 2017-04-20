package com.matcha.lock;

/**
 * Created by Administrator on 2017/4/20.
 */
public class TestSynchronized
{
    public static void main(String[] args)
    {
        Thread thread1 = new Thread(new SynchronizedRunnable(true));
        Thread thread2 = new Thread(new SynchronizedRunnable(false));
        thread1.start();
        thread2.start();
    }
}

class Synchronized
{
    private static volatile int count = 0;

    public static synchronized void write1()
    {
        count = 1;
    }

    public static void write2()
    {
        //write1就是锁的Synchronized.class，这里会阻塞
        synchronized (Synchronized.class)
        {
            count = 2;
        }
    }
}

class SynchronizedRunnable implements Runnable
{
    private boolean oneOrTwo;

    public SynchronizedRunnable(boolean oneOrTwo)
    {
        this.oneOrTwo = oneOrTwo;
    }

    @Override
    public void run()
    {
        if(oneOrTwo)
            Synchronized.write1();
        else
            Synchronized.write2();
    }
}
