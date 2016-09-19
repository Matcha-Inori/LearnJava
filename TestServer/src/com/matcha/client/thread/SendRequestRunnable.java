package com.matcha.client.thread;

import com.matcha.client.SimulateClient;

import java.net.Socket;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SendRequestRunnable implements Runnable
{
    @Override
    public void run()
    {
        SimulateClient simulateClient = new SimulateClient();
        simulateClient.sendRequest();
    }
}
