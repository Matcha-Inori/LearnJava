package com.matcha.server;

import com.matcha.server.thread.SimulateThreadFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
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
 * Created by Matcha on 16/9/19.
 */
public class StartNIOServer
{
    private static volatile StartNIOServer instance;
    private static final AtomicInteger nextId;

    static
    {
        nextId = new AtomicInteger(0);
        instance = null;
    }

    public static StartNIOServer getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new StartNIOServer("MathcaServer", 8668, 30);
    }

    private String serverName;
    private int id;
    private int port;
    private volatile boolean stop;
    private ExecutorService threadPool;

    private StartNIOServer(String serverName, int port, int maxThreadNumber)
    {
        this.serverName = serverName;
        this.port = port;
        this.id = nextId();
        this.stop = false;
        this.threadPool = Executors.newFixedThreadPool(maxThreadNumber, new SimulateThreadFactory());
    }

    public static void main(String[] args)
    {
        StartNIOServer startNIOServer = getInstance();
        startNIOServer.startServer();
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
                    StartNIOServer.this.startServer(false);
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
                ServerSocketChannel serverSocketChannel = prepareServerSocketChannel(selector)
        )
        {
            while(!stop)
            {
                int selectCount = selector.select();
                System.out.println(serverName + " --- selectCount is " + selectCount);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                SelectionKey selectionKey = null;
                ServerSocketChannel selectedSSC = null;
                SocketChannel socketChannel = null;
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
                        socketChannel.register(selector, SelectionKey.OP_READ, serverName);
                        continue;
                    }
                    if(selectionKey.isReadable())
                    {

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

    private ServerSocketChannel prepareServerSocketChannel(Selector selector) throws IOException
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverName);
        serverSocketChannel.configureBlocking(false);
        return serverSocketChannel;
    }

    private static int nextId()
    {
        return nextId.getAndIncrement();
    }
}
