package com.matcha.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Administrator on 2016/8/8.
 */
public class SimulateServlet
{
    private static SimulateServlet instance;
    private static final String[] backStrs;

    static
    {
        instance = null;
        backStrs = new String[]{
            "断剑重铸之日,骑士归来之时\n",
            "你好\n",
            "hello!\n",
            "welcome to china!\n",
            "我们一起玩吧!\n",
            "我的朋友很少\n",
            "is that is true?\n"
        };
    }

    private Random random;

    private SimulateServlet()
    {
        this.random = new Random();
    }

    public static SimulateServlet getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private synchronized static void createInstance()
    {
        if(instance == null)
            instance = new SimulateServlet();
    }

    public void dispatchRequest(Socket socket)
    {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try
        {
            Objects.requireNonNull(socket, "socket is null!");
            Thread currentThread = Thread.currentThread();
            InetAddress inetAddress = socket.getInetAddress();
            System.out.println("[" + currentThread.getName() + "] - dispatch request from " + inetAddress);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String readStr = bufferedReader.readLine();
            System.out.println("[" + currentThread.getName() + "] - read " + readStr);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            int backStrIndex = random.nextInt(backStrs.length);
            String writeStr = backStrs[backStrIndex];
            bufferedWriter.write(writeStr);
            bufferedWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if(bufferedReader != null)
                    bufferedReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if(bufferedWriter != null)
                    bufferedWriter.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if(socket != null)
                    socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
