package com.matcha.server;

import com.matcha.thread.DispatchRequestRunnable;
import com.matcha.thread.SimulateThreadFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/8/8.
 */
public class StartServer
{
    public static void main(String[] args)
    {
        ExecutorService threadPool = null;
        try(ServerSocket serverSocket = new ServerSocket(8656))
        {
            threadPool = Executors.newFixedThreadPool(3, new SimulateThreadFactory());
            Socket socket = null;
            while(true)
            {
                socket = serverSocket.accept();
                threadPool.submit(new DispatchRequestRunnable(socket));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            if(threadPool != null)
                threadPool.shutdownNow();
        }
    }
}
