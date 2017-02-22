package com.matcha.lock.app;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Administrator on 2017/2/22.
 */
public class TwinsLock implements Lock
{
    private Sync sync;

    public TwinsLock(boolean fair, int count)
    {
        sync = fair ? new FairSync(count) : new NonFairSync(count);
    }

    @Override
    public void lock()
    {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException
    {
        sync.lockInterruptibly();
    }

    @Override
    public boolean tryLock()
    {
        return sync.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        return sync.tryLock(time, unit);
    }

    @Override
    public void unlock()
    {
        sync.unlock();
    }

    @Override
    public Condition newCondition()
    {
        return sync.newCondition();
    }

    private abstract class Sync extends AbstractQueuedSynchronizer
    {
        private ThreadLocal<Counter> counterHolder;

        protected Sync(int count)
        {
            counterHolder = new ThreadLocal<>();
            this.setState(count);
        }

        protected void lock()
        {
            this.acquireShared(1);
        }

        protected void lockInterruptibly() throws InterruptedException
        {
            this.acquireSharedInterruptibly(1);
        }

        protected boolean tryLock(long time, TimeUnit unit) throws InterruptedException
        {
            return this.tryAcquireSharedNanos(1, unit.toNanos(time));
        }

        protected boolean tryLock()
        {
            return this.tryAcquireShared(1) >= 0;
        }

        protected void unlock()
        {
            this.releaseShared(1);
        }

        protected abstract boolean shouldAcquire();

        protected Condition newCondition()
        {
            return new ConditionObject();
        }

        @Override
        protected int tryAcquireShared(int arg)
        {
            //1、首先判断当前线程是否已经获取过锁
            Counter counter = this.counterHolder.get();
            if(counter != null)
                return counter.incrementAndGet();
            //2、判断一下是否应该竞争锁
            if(!shouldAcquire())
                return -1;
            //3、没有获得过锁，那么CAS获取锁状态
            int state;
            int newState;
            while(true)
            {
                state = this.getState();
                newState = state - arg;
                if(newState < 0)
                    return newState;
                if(this.compareAndSetState(state, newState))
                {
                    this.counterHolder.set(counter = new Counter());
                    return counter.incrementAndGet();
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg)
        {
            Counter counter = this.counterHolder.get();
            int count = counter.decrementAndGet();
            if(count > 0)
                return false;
            this.counterHolder.remove();
            int state;
            int newState;
            while(true)
            {
                state = this.getState();
                newState = state + arg;
                if(this.compareAndSetState(state, newState))
                    return true;
            }
        }
    }

    private class FairSync extends Sync
    {
        public FairSync(int count)
        {
            super(count);
        }

        @Override
        protected boolean shouldAcquire()
        {
            return !hasQueuedPredecessors();
        }
    }

    private class NonFairSync extends Sync
    {
        public NonFairSync(int count)
        {
            super(count);
        }

        @Override
        protected boolean shouldAcquire()
        {
            return true;
        }
    }

    private class Counter
    {
        private int count;

        public Counter()
        {
            count = 0;
        }

        int incrementAndGet()
        {
            return ++count;
        }

        int decrementAndGet()
        {
            return --count;
        }
    }
}
