package com.matcha.lock.app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by rd_xidong_ren on 2016/7/22.
 */
public class Cache
{
    private static Cache instance;

    static
    {
        instance = null;
    }

    private volatile Map<Integer, String> map;
    private ReadWriteLock readWriteLock;
    private Lock readLock;
    private Lock writeLock;

    private Cache()
    {
        map = new HashMap<Integer, String>();
        readWriteLock = new ReentrantReadWriteLock(true);
        writeLock = readWriteLock.writeLock();
        readLock = readWriteLock.readLock();
    }

    public static Cache getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new Cache();
    }

    public final String get(int key)
    {
        readLock.lock();
        try
        {
            return map.get(key);
        }
        finally
        {
            readLock.unlock();
        }
    }

    public final void set(int key, String value)
    {
        writeLock.lock();
        try
        {
            map.put(key, value);
        }
        finally
        {
            writeLock.unlock();
        }
    }
}
