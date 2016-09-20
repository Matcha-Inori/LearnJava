package com.matcha.nio.test.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ConnectThread extends IdedThread
{
    private int port;

    public ConnectThread(int port)
    {
        this.port = port;
        this.setName("ConnectThread - " + id);
    }

    @Override
    public void run()
    {
        try(
                SocketChannel socketChannel = SocketChannel.open()
        )
        {
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8668));
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);
            byteBuffer.put("Hello world".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
