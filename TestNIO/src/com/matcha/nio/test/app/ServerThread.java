package com.matcha.nio.test.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ServerThread extends IdedThread
{
    private int port;
    private volatile boolean stop;

    public ServerThread(int port)
    {
        this.port = port;
        this.setName("ServerThread - " + id);
        this.stop = false;
    }

    @Override
    public void run()
    {
        try(
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        )
        {
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8668));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            Set<SelectionKey> selectedKeys = null;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);
            while(!stop)
            {
                int selected = selector.select(100);
                if(selected <= 0)
                    continue;
                selectedKeys = selector.selectedKeys();
                server(selectedKeys, byteBuffer);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void server(Set<SelectionKey> selectedKeys, ByteBuffer byteBuffer) throws IOException
    {
        Iterator<SelectionKey> selectedKeyIter = selectedKeys.iterator();
        SelectionKey selectionKey = null;
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        Selector selector = null;
        byte[] clientRequestBytes = null;
        while(selectedKeyIter.hasNext())
        {
            selectionKey = selectedKeyIter.next();
            selectedKeyIter.remove();
            selector = selectionKey.selector();
            if(selectionKey.isAcceptable())
            {
                serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
                continue;
            }

            if(selectionKey.isReadable())
            {
                socketChannel = (SocketChannel) selectionKey.channel();
                int readSize = socketChannel.read(byteBuffer);
                if(readSize == -1)
                {
                    socketChannel.close();
                    continue;
                }
                byteBuffer.flip();
                int remaining = byteBuffer.remaining();
                clientRequestBytes = new byte[remaining];
                byteBuffer.get(clientRequestBytes);
                byteBuffer.clear();
                System.out.println(new String(clientRequestBytes));
            }
        }
    }

    private void stopServer()
    {
        this.stop = true;
    }
}
