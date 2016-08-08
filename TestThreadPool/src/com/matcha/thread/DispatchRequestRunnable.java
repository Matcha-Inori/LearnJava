package com.matcha.thread;

import com.matcha.server.SimulateServlet;

import java.net.Socket;

/**
 * Created by Matcha on 16/8/8.
 */
public class DispatchRequestRunnable implements Runnable
{
    private Socket socket;

    public DispatchRequestRunnable(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        SimulateServlet simulateServlet = SimulateServlet.getInstance();
        simulateServlet.dispatchRequest(socket);
    }
}
