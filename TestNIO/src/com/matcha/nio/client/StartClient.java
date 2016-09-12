package com.matcha.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/12.
 */
public class StartClient
{
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args)
    {
        SocketAddress socketAddress = new InetSocketAddress("localhost", 8668);
        try(SocketChannel socketChannel = prepareSocketChannel(socketAddress, false))
        {
            int count = 0;
            while(!socketChannel.finishConnect())
            {
                if(count == 3)
                    throw new RuntimeException("can not connect the server !!! ");
                count++;
                Thread.sleep(10);
            }

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
            byteBuffer.put("Hello server !!! I'm client !!!".getBytes(charset));
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            int remaining = byteBuffer.remaining();
            if(remaining > 0)
            {
                byte[] bytes = new byte[remaining];
                byteBuffer.get(bytes);
                String response = new String(bytes, charset);
                System.out.println("server response : " + response);
            }
            else
                System.out.println("server has no response");
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static SocketChannel prepareSocketChannel(SocketAddress socketAddress, boolean block) throws IOException
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(block);
        socketChannel.connect(socketAddress);
        return socketChannel;
    }
}
