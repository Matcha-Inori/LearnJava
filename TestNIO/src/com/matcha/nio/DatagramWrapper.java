package com.matcha.nio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by Matcha on 16/9/13.
 */
public class DatagramWrapper
{
    private DatagramChannel datagramChannel;
    private DatagramSocket datagramSocket;

    public DatagramWrapper(DatagramChannel datagramChannel)
    {
        this.datagramChannel = datagramChannel;
    }

    public DatagramWrapper(DatagramSocket datagramSocket)
    {
        this.datagramSocket = datagramSocket;
    }

    public void connect(SocketAddress socketAddress) throws IOException
    {
        if(datagramChannel != null)
            datagramChannel.connect(socketAddress);

        if(datagramSocket != null)
            datagramSocket.connect(socketAddress);
    }

    public void disconnect() throws IOException
    {
        if(datagramChannel != null)
            datagramChannel.disconnect();

        if(datagramSocket != null)
            datagramSocket.disconnect();
    }

    public void send(DatagramPacket datagramPacket) throws IOException
    {
        if(datagramSocket != null)
            datagramSocket.send(datagramPacket);
    }

    public void send(ByteBuffer dataBuffer, SocketAddress socketAddress) throws IOException
    {
        if(datagramChannel != null)
            datagramChannel.send(dataBuffer, socketAddress);
    }

    public boolean isConnected()
    {
        if(datagramChannel != null)
            return datagramChannel.isConnected();

        if(datagramSocket != null)
            return datagramSocket.isConnected();

        return true;
    }
}
