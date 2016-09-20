package com.matcha.nio.server.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/12.
 */
public class StartServer
{
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args)
    {
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open())
        {
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(8668));
            SocketChannel socketChannel;
            while(true)
            {
                socketChannel = serverSocketChannel.accept();
                if(socketChannel == null)
                    continue;
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
                socketChannel.read(byteBuffer);
                byteBuffer.flip();
                int remaining = byteBuffer.remaining();
                byte[] bytes = new byte[remaining];
                byteBuffer.get(bytes);
                String request = new String(bytes, charset);
                System.out.println("client request is : " + request);
                byteBuffer.clear();
                byteBuffer.put("Hi !!! This is Mathca server !!! ".getBytes(charset));
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                socketChannel.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
