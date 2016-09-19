package com.matcha.client;

import com.matcha.client.thread.SendRequestRunnable;
import com.matcha.client.thread.SimulateClientThreadFacotry;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Administrator on 2016/8/9.
 */
public class StartClient
{
    private static boolean stop = false;

    public static void main(String[] args)
    {
        Thread sendRequestThread = null;
        ThreadFactory threadFactory = new SimulateClientThreadFacotry();
        while(!stop)
        {
            sendRequestThread = threadFactory.newThread(new SendRequestRunnable());
            sendRequestThread.start();
        }
    }
}
