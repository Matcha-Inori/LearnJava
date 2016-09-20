package com.matcha.nio.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by Administrator on 2016/9/20.
 */
public class TestSelector
{
    public static void main(String[] args)
    {
        try(
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        )
        {
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8668));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            Thread selectThread = new Thread(new Runnable(){

                @Override
                public void run()
                {
                    try
                    {
                        int selected = selector.select();
                        System.out.println(selected);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    System.out.println("select finished");
                }

            }, "SelectThread");
            selectThread.start();
            Thread closeThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        selector.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    System.out.println("close finished");
                }
            }, "CloseThread");
            closeThread.start();
            selectThread.join();
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
