package com.matcha.nio.test;

import com.matcha.nio.test.app.ClientThread;
import com.matcha.nio.test.app.ServerThread;

/**
 * Created by Administrator on 2016/9/20.
 */
public class TestSelectStatus
{
    public static void main(String[] args)
    {
        try
        {
            int port = 8668;
            Thread serverThread = new ServerThread(port);
            Thread clientThread1 = new ClientThread(port);
            Thread clientThread2 = new ClientThread(port);
            serverThread.start();
            clientThread1.start();
            clientThread2.start();
            serverThread.join();
            clientThread1.join();
            clientThread2.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
