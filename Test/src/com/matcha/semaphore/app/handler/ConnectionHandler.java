package com.matcha.semaphore.app.handler;

import com.matcha.semaphore.app.Connection;
import com.matcha.semaphore.app.SimulateConnectionPool;
import com.sun.xml.internal.ws.Closeable;

import java.util.Objects;

/**
 * Created by Administrator on 2016/8/8.
 */
public class ConnectionHandler implements Closeable
{
    private final SimulateConnectionPool simulateConnectionPool;
    private final Connection connection;

    public ConnectionHandler(SimulateConnectionPool simulateConnectionPool, Connection connection)
    {
        Objects.requireNonNull(simulateConnectionPool, "simulateConnectionPool cannot be null!");
        Objects.requireNonNull(connection, "connection cannot be null!");
        this.simulateConnectionPool = simulateConnectionPool;
        this.connection = connection;
    }

    @Override
    public void close()
    {
        simulateConnectionPool.releaseConnection(connection);
    }

    @Override
    public String toString()
    {
        return connection.toString();
    }
}
