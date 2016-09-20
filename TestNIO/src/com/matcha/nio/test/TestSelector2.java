package com.matcha.nio.test;

import com.matcha.nio.test.app.CloseThread;
import com.matcha.nio.test.app.SelectThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by Administrator on 2016/9/20.
 */
public class TestSelector2
{
    public static void main(String[] args)
    {
        try(
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        )
        {
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8668));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            Thread selectThread1 = new SelectThread(selector);
            Thread selectThread2 = new SelectThread(selector);
            Thread closeThread = new CloseThread(selector);
            selectThread1.start();
            selectThread2.start();
            closeThread.start();
            selectThread1.join();
            selectThread2.join();
            closeThread.join();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
