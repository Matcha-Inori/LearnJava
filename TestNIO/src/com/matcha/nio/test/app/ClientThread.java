package com.matcha.nio.test.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ClientThread extends IdedThread
{
    private int port;

    public ClientThread(int port)
    {
        this.port = port;
        this.setName("ClientThread - " + id);
    }

    @Override
    public void run()
    {
        try(
                SocketChannel socketChannel = SocketChannel.open()
        )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);
            socketChannel.connect(new InetSocketAddress("localhost", port));
            socketChannel.configureBlocking(false);
            byteBuffer.put("你好！！！！这里是客户端!!!".getBytes());
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
