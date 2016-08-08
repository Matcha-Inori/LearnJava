package com.matcha.server;

import java.net.ServerSocket;

/**
 * Created by Administrator on 2016/8/8.
 */
public class SimulateServlet
{
    private static SimulateServlet instance;

    private SimulateServlet()
    {

    }

    public static SimulateServlet getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    public synchronized static void createInstance()
    {
        if(instance == null)
            instance = new SimulateServlet();
    }

    public void startServer()
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(8586);
        }
    }
}
