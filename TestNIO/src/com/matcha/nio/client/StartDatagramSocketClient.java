package com.matcha.nio.client;

import com.matcha.nio.DatagramSocketServerMehod;
import com.matcha.nio.DatagramWrapper;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/12.
 */
public class StartDatagramSocketClient
{
    private static Charset charset = Charset.forName("UTF-8");
    private static boolean oldSocket = false;

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
            datagramChannel.bind(new InetSocketAddress("localhost", 8778));
            SocketAddress serverAddress = new InetSocketAddress("localhost", 8669);
            datagramChannel.connect(serverAddress);
            DatagramWrapper datagramWrapper = new DatagramWrapper(datagramChannel);
            sendMessage(serverAddress, datagramWrapper);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void oldSocket()
    {
        try(DatagramSocket datagramSocket = new DatagramSocket(new InetSocketAddress("localhost", 8778)))
        {
            SocketAddress serverAddress = new InetSocketAddress("localhost", 8669);
            DatagramWrapper datagramWrapper = new DatagramWrapper(datagramSocket);
            sendMessage(serverAddress, datagramWrapper);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void sendMessage(SocketAddress socketAddress,
                                    DatagramWrapper datagramWrapper) throws IOException
    {
        ByteBuffer sendDataBuffer = ByteBuffer.allocateDirect(128);

        sendDataBuffer.putInt(DatagramSocketServerMehod.CONNECT);
        sendMessage(sendDataBuffer, socketAddress, datagramWrapper);

        //在使用Channel的时候，connect方法
//        try
//        {
//            Thread.sleep(2000);
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }

        sendDataBuffer.putInt(DatagramSocketServerMehod.PRINT);
        sendDataBuffer.put("Hello!!! I'm LiSA!!!".getBytes(charset));
        sendMessage(sendDataBuffer, socketAddress, datagramWrapper);

        sendDataBuffer.putInt(DatagramSocketServerMehod.PRINT);
        sendDataBuffer.put("欢迎收看LiSA TV!!!".getBytes(charset));
        sendMessage(sendDataBuffer, socketAddress, datagramWrapper);

        sendDataBuffer.putInt(DatagramSocketServerMehod.DISCONNECT);
        sendMessage(sendDataBuffer, socketAddress, datagramWrapper);

    }

    private static void sendMessage(ByteBuffer sendDataBuffer,
                                    SocketAddress serverAddress,
                                    DatagramWrapper datagramWrapper) throws IOException
    {
        sendDataBuffer.flip();
        if(oldSocket)
        {
            DatagramPacket datagramPacket = prepareDatagramPacket(serverAddress, sendDataBuffer);
            datagramWrapper.send(datagramPacket);
        }
        else
            datagramWrapper.send(sendDataBuffer, serverAddress);
        sendDataBuffer.clear();
    }

    private static DatagramPacket prepareDatagramPacket(SocketAddress serverAddress, ByteBuffer sendDataBuffer)
    {
        if(!sendDataBuffer.hasRemaining())
            return null;
        int remaining = sendDataBuffer.remaining();
        byte[] sendDataBytes = new byte[remaining];
        sendDataBuffer.get(sendDataBytes);
        DatagramPacket datagramPacket = new DatagramPacket(sendDataBytes, sendDataBytes.length);
        datagramPacket.setSocketAddress(serverAddress);
        return datagramPacket;
    }
}
