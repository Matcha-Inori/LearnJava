package com.matcha.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by rd_xidong_ren on 2016/7/25.
 */
public class TestLockDown
{
    public static void main(String[] args)
    {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        readLock.lock();
        writeLock.unlock();
        readLock.unlock();
    }
}
