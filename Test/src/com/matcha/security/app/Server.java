package com.matcha.security.app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.AccessControlContext;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Matcha on 2016/12/14.
 */
public class Server
{
    private int port;
    private boolean stop;

    public Server(int port)
    {
        this.port = port;
        this.stop = false;
    }

    public void start()
    {
        try(
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                Selector selector = Selector.open()
        )
        {
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            int selected = 0;
            Set<SelectionKey> selectionKeys = null;
            Iterator<SelectionKey> keyIterator = null;
            SelectionKey selectionKey = null;

            ServerSocketChannel selectedSSC = null;
            SocketChannel socketChannel = null;

            ByteBuffer argIntBuffer = ByteBuffer.allocateDirect(4 * 8);
            ByteBuffer argCtxBuffer = ByteBuffer.allocateDirect(1024);
            byte[] readBytes = new byte[1024];
            byte[] argCtxBytes = null;

            AccessControlContext argCtx = null;

            Integer result = null;

            while(true)
            {
                selected = selector.select(100);
                if(selected == 0 && stop)
                    break;
                else if(selected == 0)
                    continue;
                selectionKeys = selector.selectedKeys();
                keyIterator = selectionKeys.iterator();
                while(keyIterator.hasNext())
                {
                    selectionKey = keyIterator.next();
                    keyIterator.remove();
                    if(selectionKey.isAcceptable())
                    {
                        selectedSSC = (ServerSocketChannel) selectionKey.channel();
                        socketChannel = selectedSSC.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                    if(selectionKey.isReadable())
                    {
                        socketChannel = (SocketChannel) selectionKey.channel();
                        argIntBuffer.clear();
                        int startValue = socketChannel.read(argIntBuffer);
                        if(startValue == -1)
                        {
                            selectionKey.cancel();
                            socketChannel.close();
                            continue;
                        }
                        argIntBuffer.flip();
                        int argInt = argIntBuffer.getInt();
                        argCtxBuffer.clear();
                        while(socketChannel.read(argCtxBuffer) != 0)
                        {
                            int remaining = argCtxBuffer.remaining();
                            argCtxBuffer.get(readBytes);
                            if(argCtxBytes == null)
                                argCtxBytes = Arrays.copyOf(readBytes, remaining);
                            else
                            {
                                int oldLength = argCtxBytes.length;
                                argCtxBytes = Arrays.copyOf(argCtxBytes, oldLength + remaining);
                                System.arraycopy(readBytes, 0, argCtxBytes, oldLength, remaining);
                            }
                        }
                        argCtx = readContext(argCtxBytes);
                        result = dispatchFig(argInt, argCtx);
                        selectionKey.attach(result);
                        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
                    }

                    if(selectionKey.isWritable())
                    {
                        socketChannel = (SocketChannel) selectionKey.channel();
                        result = (Integer) selectionKey.attachment();
                        selectionKey.attach(null);
                        argIntBuffer.putInt(result);
                        argIntBuffer.flip();
                        socketChannel.write(argIntBuffer);
                        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
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

    private AccessControlContext readContext(byte[] argCtxBytes)
    {
        try(
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(argCtxBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
        )
        {
            return (AccessControlContext) objectInputStream.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Integer dispatchFig(int argInt, AccessControlContext argCtx)
    {
        FibService fibService = FibService.getInstance();
        return fibService.fib(argInt, argCtx);
    }
}
