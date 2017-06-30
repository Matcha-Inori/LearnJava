package com.matcha.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/30.
 */
public class ConnectionPool
{
    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Semaphore semaphore;

    private Set<Connection> usedConnectionSet;
    private BlockingQueue<Connection> freeConnectionQueue;

    private String dbConnectionUrl;
    private String userName;
    private String password;

    public ConnectionPool(String dbConnectionUrl, String userName, String password, int maxConnectionCount)
    {
        this.dbConnectionUrl = dbConnectionUrl;
        this.userName = userName;
        this.password = password;
        this.semaphore = new Semaphore(maxConnectionCount);
        this.usedConnectionSet = new HashSet<>(maxConnectionCount);
        this.freeConnectionQueue = new LinkedBlockingQueue<>(maxConnectionCount);
    }

    public Connection getConnection()
    {
        return this.getConnection(0, TimeUnit.MILLISECONDS);
    }

    public Connection getConnection(long timeout, TimeUnit timeUnit)
    {
        try
        {
            semaphore.tryAcquire(timeout, timeUnit);
            Connection connection = freeConnectionQueue.poll();
            if(connection == null)
                connection = createConnection();
            usedConnectionSet.add(connection);
            return connection;
        }
        catch (InterruptedException e)
        {
        }
        return null;
    }

    public boolean releaseConnection(Connection connection)
    {
        if(!usedConnectionSet.contains(connection)) return false;
        semaphore.release();
        usedConnectionSet.remove(usedConnectionSet);
        return freeConnectionQueue.offer(connection);
    }

    private Connection createConnection()
    {
        try
        {
            return DriverManager.getConnection(dbConnectionUrl, userName, password);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
