package com.matcha.nio.session;

import com.matcha.nio.exception.MatchaUnCheckException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2016/9/21.
 */
public class Session
{
    private UUID sessionId;
    private String userName;
    private Date loginDate;
    private Map sessionInfo;
    private ReentrantReadWriteLock readWriteLock;
    private Lock readLock;
    private Lock writeLock;

    public Session(String userName)
    {
        this.userName = userName;

        this.sessionId = UUID.randomUUID();
        this.loginDate = new Date();
        this.sessionInfo = new HashMap();
        this.readWriteLock = new ReentrantReadWriteLock(false);
        this.readLock = this.readWriteLock.readLock();
        this.writeLock = this.readWriteLock.writeLock();
    }

    public Object put(Object key,
                      Object value,
                      long time,
                      TimeUnit timeUnit)
    {
        boolean locked = false;
        try
        {
            locked = this.writeLock.tryLock(time, timeUnit);
            if(locked)
                return sessionInfo.put(key, value);
            else
                return null;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new MatchaUnCheckException(e);
        }
        finally
        {
            if(locked)
                this.writeLock.unlock();
        }
    }

    public Object put(Object key, Object value)
    {
        this.writeLock.lock();
        try
        {
            return sessionInfo.put(key, value);
        }
        finally
        {
            this.writeLock.unlock();
        }
    }

    public Object get(Object key,
                      Object defaultValue,
                      long time,
                      TimeUnit timeUnit)
    {
        boolean locked = false;
        try
        {
            locked = this.readLock.tryLock(time, timeUnit);
            if(locked)
            {
                Object value = sessionInfo.get(key);
                return value == null ? defaultValue : value;
            }
            else
                return defaultValue;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new MatchaUnCheckException(e);
        }
        finally
        {
            if(locked)
                this.writeLock.unlock();
        }
    }

    public Object get(Object key, long time, TimeUnit timeUnit)
    {
        return get(key, null, time, timeUnit);
    }

    public Object get(Object key, Object defaultValue)
    {
        this.readLock.lock();
        try
        {
            Object value = sessionInfo.get(key);
            return value == null ? defaultValue : value;
        }
        finally
        {
            this.readLock.unlock();
        }
    }

    public Object get(Object key)
    {
        this.readLock.lock();
        try
        {
            return sessionInfo.get(key);
        }
        finally
        {
            this.readLock.unlock();
        }
    }

    public boolean containsKey(Object key)
    {
        this.readLock.lock();
        try
        {
            return sessionInfo.containsKey(key);
        }
        finally
        {
            this.readLock.unlock();
        }
    }

    public boolean containsValue(Object value)
    {
        this.readLock.lock();
        try
        {
            return sessionInfo.containsValue(value);
        }
        finally
        {
            this.readLock.unlock();
        }
    }

    public String getUserName()
    {
        return userName;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    @Override
    public String toString()
    {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("Session{").append("sessionId=").append(sessionId).append(", userName='")
                .append(userName).append('\'').append(", loginDate=").append(loginDate)
                .append(", sessionInfo=").append(sessionInfo).append('}');
        return strBuffer.toString();
    }
}
