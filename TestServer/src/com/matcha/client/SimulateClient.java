package com.matcha.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Matcha on 16/8/8.
 */
public class SimulateClient
{
    private static final String[] sendStrs;

    static
    {
        sendStrs = new String[]{
            "访问门户页面\n",
            "请求js\n",
            "request css file\n",
            "login\n",
            "注销\n",
            "download file\n"
        };
    }

    private UUID clientId;
    private Random random;

    public SimulateClient()
    {
        clientId = UUID.randomUUID();
        this.random = new Random();
    }

    public void sendRequest()
    {
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        try(Socket socket = new Socket("127.0.0.1", 8656))
        {
            Thread currentThread = Thread.currentThread();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            int sendStrIndex = random.nextInt(sendStrs.length);
            System.out.println("[" + currentThread.getName() + "] - client " + clientId.toString() + " send request ");
            bufferedWriter.write(sendStrs[sendStrIndex]);
            bufferedWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String readStr = bufferedReader.readLine();
            System.out.println("[" + currentThread.getName() + "] - get response " + readStr);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
