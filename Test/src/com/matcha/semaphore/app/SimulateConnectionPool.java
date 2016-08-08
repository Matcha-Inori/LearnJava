package com.matcha.semaphore.app;

import com.matcha.semaphore.app.handler.ConnectionHandler;
import com.matcha.semaphore.exception.NoConnectionException;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2016/8/8.
 */
public class SimulateConnectionPool
{
    private volatile static SimulateConnectionPool instance;

    private ConcurrentLinkedQueue<Connection> concurrentLinkedQueue;

    private SimulateConnectionPool(int size)
    {
        concurrentLinkedQueue = new ConcurrentLinkedQueue<Connection>();
        for(int i = 0;i < size;i++)
            concurrentLinkedQueue.add(new Connection());
    }

    public static SimulateConnectionPool getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    public static synchronized void createInstance()
    {
        if(instance == null)
            instance = new SimulateConnectionPool(10);
    }

    public ConnectionHandler getConnection() throws NoConnectionException
    {
        Connection connection = concurrentLinkedQueue.poll();
        if(connection == null)
            throw new NoConnectionException();
        return new ConnectionHandler(this, connection);
    }

    public void releaseConnection(Connection connection)
    {
        if(connection == null)
            return;
        concurrentLinkedQueue.offer(connection);
    }

}
