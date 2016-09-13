package com.matcha.nio.server.main;

import com.matcha.nio.DatagramSocketServerMehod;
import com.matcha.nio.DatagramWrapper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/13.
 */
public class StartDatagramSocketServer
{
    private static boolean oldSocket = false;
    private static Charset charset = Charset.forName("UTF-8");

    private static SocketAddress connectedClientAddress = null;

    public static void main(String[] args)
    {
        if(oldSocket)
            oldSocket();
        else
            newSocket();
    }

    private static void newSocket()
    {
        try(DatagramChannel datagramChannel = DatagramChannel.open())
        {
            datagramChannel.configureBlocking(false);
            datagramChannel.bind(new InetSocketAddress(8669));
            ByteBuffer dataByteBuffer = ByteBuffer.allocateDirect(128);
            SocketAddress clientAddress = null;
            DatagramWrapper datagramWrapper = new DatagramWrapper(datagramChannel);
            while(true)
            {
                clientAddress = datagramChannel.receive(dataByteBuffer);
                if(clientAddress == null)
                    continue;
                dataByteBuffer.flip();
                server(dataByteBuffer, clientAddress, datagramWrapper);
                dataByteBuffer.clear();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void oldSocket()
    {
        try(DatagramSocket datagramSocket = new DatagramSocket(8669))
        {
            byte[] dataBytes = new byte[128];
            DatagramPacket datagramPacket = new DatagramPacket(dataBytes, dataBytes.length);
            DatagramWrapper datagramWrapper = new DatagramWrapper(datagramSocket);
            while(true)
            {
                datagramSocket.receive(datagramPacket);
                SocketAddress clientAddress = datagramPacket.getSocketAddress();
                ByteBuffer clientDataBuffer = ByteBuffer.wrap(datagramPacket.getData(), 0, datagramPacket.getLength());
                server(clientDataBuffer, clientAddress, datagramWrapper);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void server(ByteBuffer clientDataBuffer,
                               SocketAddress clientAddress,
                               DatagramWrapper datagramWrapper) throws IOException
    {
        if(connectedClientAddress != null
                && !clientAddress.equals(connectedClientAddress))
            return;

        byte[] clientDataCharBytes = null;
        String clientStr = null;

        if(!clientDataBuffer.hasRemaining())
            return;
        int method = clientDataBuffer.getInt();
        switch(method)
        {
            case DatagramSocketServerMehod.CONNECT:
            {
                if(datagramWrapper.isConnected())
                    return;
                datagramWrapper.connect(clientAddress);
                connectedClientAddress = clientAddress;
                System.out.println("Connect : " + clientAddress);
                break;
            }
            case DatagramSocketServerMehod.PRINT:
            {
                if(!clientDataBuffer.hasRemaining())
                    return;
                int remaining = clientDataBuffer.remaining();
                clientDataCharBytes = new byte[remaining];
                clientDataBuffer.get(clientDataCharBytes);
                clientStr = new String(clientDataCharBytes, charset);
                System.out.println(clientStr);
                break;
            }
            case DatagramSocketServerMehod.DISCONNECT:
            {
                //same with default
            }
            default:
            {
                System.out.println("Disconnect : " + connectedClientAddress);
                connectedClientAddress = null;
                datagramWrapper.disconnect();
                break;
            }
        }
    }
}
