package com.matcha.security.client;

import com.sun.istack.internal.ByteArrayDataSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.Scanner;

/**
 * Created by Matcha on 2016/12/14.
 */
public class Client
{
    private int serverPort;

    public Client(int serverPort)
    {
        this.serverPort = serverPort;
    }

    public void startClient()
    {
        Scanner scanner = new Scanner(System.in);
        String nextLine = null;
        while(true)
        {
            if(scanner.hasNextInt())
            {
                int nextInt = scanner.nextInt();
                int result = askServer(nextInt);
                System.out.println(result);
            }
        }
    }

    private int askServer(int argInt)
    {
        try(
                SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(serverPort))

        )
        {
            byte[] argCtxBytes = writeContext();
            ByteBuffer argIntBuffer = ByteBuffer.allocateDirect(4 * 8);
            ByteBuffer argCtxBuffer = ByteBuffer.allocateDirect(argCtxBytes.length);
            argIntBuffer.putInt(argInt);
            argCtxBuffer.put(argCtxBuffer);
            argIntBuffer.flip();
            argCtxBuffer.flip();
            socketChannel.write(new ByteBuffer[]{argIntBuffer, argCtxBuffer});
            argIntBuffer.clear();
            socketChannel.read(argIntBuffer);
            argIntBuffer.flip();
            return argIntBuffer.getInt();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private byte[] writeContext()
    {
        AccessControlContext argCtx = AccessController.getContext();
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        )
        {
            objectOutputStream.writeObject(argCtx);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
