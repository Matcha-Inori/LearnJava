package com.matcha.nio.server;

import com.matcha.nio.session.Session;
import com.matcha.server.thread.SimulateThreadFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MatchaNIOServer
{
    private static final AtomicInteger nextId;

    static
    {
        nextId = new AtomicInteger(0);
    }

    private static int nextId()
    {
        return nextId.getAndIncrement();
    }

    private String serverName;
    private String address;
    private int port;

    private int id;
    private volatile boolean stop;
    private ExecutorService threadPool;

    public MatchaNIOServer(String serverName, String address, int port, int maxThreadNumber)
    {
        this.serverName = serverName;
        this.address = address;
        this.port = port;
        this.id = nextId();
        this.stop = false;
        this.threadPool = Executors.newFixedThreadPool(maxThreadNumber, new SimulateThreadFactory());
    }

    public static void main(String[] args)
    {

    }

    public void startServer(boolean sync)
    {

        if(sync)
        {
            Thread serverThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    MatchaNIOServer.this.startServer(false);
                }
            }, "ServerThread");
            serverThread.start();
        }
        else
            startServer();
    }

    public void startServer()
    {
        try(
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        )
        {
            serverSocketChannel.bind(new InetSocketAddress(address, port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer methodBuffer = ByteBuffer.allocateDirect(128);
            Session session = null;
            SelectionKey selectionKey = null;
            ServerSocketChannel selectedSSC = null;
            SocketChannel socketChannel = null;
            Set<SelectionKey> selectionKeys = null;
            Iterator<SelectionKey> selectionKeyIterator = null;
            while(!stop)
            {
                int selectCount = selector.select();
                System.out.println(serverName + " --- selectCount is " + selectCount);
                selectionKeys = selector.selectedKeys();
                selectionKeyIterator = selectionKeys.iterator();
                while(selectionKeyIterator.hasNext())
                {
                    selectionKey = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    System.out.println(serverName + " --- attachment is " + selectionKey.attachment());
                    //能进入这个分支的仅仅可能是ServerSocketChannel
                    if(selectionKey.isAcceptable())
                    {
                        selectedSSC = (ServerSocketChannel) selectionKey.channel();
                        socketChannel = selectedSSC.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                    if(selectionKey.isReadable())
                    {
                        session = (Session) selectionKey.attachment();
                        socketChannel = selectionKey.channel();
                        socketChannel.read(methodBuffer);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
