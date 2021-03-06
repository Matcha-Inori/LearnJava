package com.matcha.db;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2017/6/30.
 */
public final class ConnectionPoolBuilder
{
    private static final Map<String, ConnectionPool> connectionPoolMap;
    private static final ReentrantReadWriteLock readWriteLock;
    private static final Lock readLock;
    private static final Lock writeLock;

    static
    {
        connectionPoolMap = new HashMap<>();
        readWriteLock = new ReentrantReadWriteLock(false);
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    private String dbConnectionUrl;
    private String userName;
    private String password;

    public ConnectionPoolBuilder()
    {
    }

    public ConnectionPoolBuilder setDbConnectionUrl(String dbConnectionUrl)
    {
        this.dbConnectionUrl = dbConnectionUrl;
        return this;
    }

    public ConnectionPoolBuilder setUserName(String userName)
    {
        this.userName = userName;
        return this;
    }

    public ConnectionPoolBuilder setPassword(String password)
    {
        this.password = password;
        return this;
    }

    private String buildDbKey(String dbConnectionUrl, String userName)
    {
        return String.format("%s@%s", dbConnectionUrl, userName);
    }

    public ConnectionPool build()
    {
        String dbKey = buildDbKey(dbConnectionUrl, userName);
        readLock.lock();
        try
        {
            ConnectionPool connectionPool = connectionPoolMap.get(dbKey);
            if(connectionPool != null) return connectionPool;
        }
        finally
        {
            readLock.unlock();
        }
        return buildConnectionPool();
    }

    private ConnectionPool buildConnectionPool()
    {
        String dbKey = buildDbKey(dbConnectionUrl, userName);
        writeLock.lock();
        try
        {
            ConnectionPool connectionPool = connectionPoolMap.get(dbKey);
            if(connectionPool != null) return connectionPool;
            connectionPool =
                    new ConnectionPool(dbConnectionUrl, userName, password, 5);
            connectionPoolMap.put(dbKey, connectionPool);
            return connectionPool;
        }
        finally
        {
            writeLock.unlock();
        }
    }
}
