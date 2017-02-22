package com.matcha.lock;

import com.matcha.lock.app.TwinsLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by Administrator on 2017/2/22.
 */
public class TestTwinsLock
{
    public static void main(String[] args)
    {
        Lock lock = new TwinsLock(true, 2);
        int getCount = 3;
        int tryGetCount = 3;
        int tryBlockGetCount = 3;
        for(int index = 0;index < getCount;index++)
            new Thread(new GetLockRunnable(lock), String.format("%s - %d", "GetThread", index)).start();

        for(int index = 0;index < tryGetCount;index++)
            new Thread(new TryGetLockRunnable(lock), String.format("%s - %d", "TryGetThread", index)).start();

        for(int index = 0;index < tryBlockGetCount;index++)
            new Thread(new TryBlockGetLockRunnable(lock), String.format("%s - %d", "TryBlockGetThread", index)).start();
    }

    private static class GetLockRunnable implements Runnable
    {
        private Lock lock;

        public GetLockRunnable(Lock lock)
        {
            this.lock = lock;
        }

        @Override
        public void run()
        {
            this.lock.lock();
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                this.lock.unlock();
            }
        }
    }

    private static class TryGetLockRunnable implements Runnable
    {
        private Lock lock;

        public TryGetLockRunnable(Lock lock)
        {
            this.lock = lock;
        }

        @Override
        public void run()
        {
            boolean getLock = this.lock.tryLock();
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                this.lock.unlock();
            }
        }
    }

    private static class TryBlockGetLockRunnable implements Runnable
    {
        private Lock lock;

        public TryBlockGetLockRunnable(Lock lock)
        {
            this.lock = lock;
        }

        @Override
        public void run()
        {
            try
            {
                boolean getLock = this.lock.tryLock(1, TimeUnit.SECONDS);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                this.lock.unlock();
            }
        }
    }
}
