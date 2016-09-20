package com.matcha.nio.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Matcha on 16/9/20.
 */
public class TestSelector3
{
    public static void main(String[] args)
    {
        try(
                SocketChannel socketChannel = SocketChannel.open();
                Selector selector1 = Selector.open();
                Selector selector2 = Selector.open();
        )
        {
            socketChannel.bind(new InetSocketAddress("localhost", 8668));
            socketChannel.configureBlocking(false);
            SelectionKey key1 = socketChannel.register(selector1, SelectionKey.OP_WRITE);
            SelectionKey key2 = socketChannel.register(selector2, SelectionKey.OP_READ);
            System.out.println(key1 == key2);
            System.out.println(key1.equals(key2));
            System.out.println(key1.interestOps());
            System.out.println(key2.interestOps());
            SelectionKey key3 = socketChannel.register(selector1, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            System.out.println(key1 == key3);
            System.out.println(key1.equals(key3));
            System.out.println(key1.interestOps());
            System.out.println(key3.interestOps());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
