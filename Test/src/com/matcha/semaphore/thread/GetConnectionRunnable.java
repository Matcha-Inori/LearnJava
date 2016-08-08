package com.matcha.semaphore.thread;

import com.matcha.semaphore.app.SimulateConnectionPool;
import com.matcha.semaphore.app.handler.ConnectionHandler;
import com.matcha.semaphore.exception.NoConnectionException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2016/8/8.
 */
public class GetConnectionRunnable implements Runnable
{
    private Semaphore semaphore;
    private CountDownLatch countDownLatch;

    public GetConnectionRunnable(Semaphore semaphore, CountDownLatch countDownLatch)
    {
        this.semaphore = semaphore;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run()
    {
        ConnectionHandler connection = null;
        try
        {
            countDownLatch.await();
            semaphore.acquire();
            SimulateConnectionPool connectionPool = SimulateConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            Thread localThread = Thread.currentThread();
            System.out.println("Thread " + localThread.getName() + " get Connection " + connection);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (NoConnectionException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            if(connection != null)
                connection.close();
            semaphore.release();
        }
    }
}
