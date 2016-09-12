package com.matcha.nio.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/12.
 */
public class StartDatagramSocketClient
{
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args)
    {
        try(DatagramSocket datagramSocket = new DatagramSocket(new InetSocketAddress("localhost", 8787)))
        {
            String needSendRequestStr = "Oh!  you can not catch me !!!";
            byte[] needSendRequest = needSendRequestStr.getBytes(charset);
            DatagramPacket datagramPacket = new DatagramPacket(needSendRequest, 0, needSendRequest.length);
            datagramPacket.setSocketAddress(new InetSocketAddress("localhost", 8668));
            datagramSocket.send(datagramPacket);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
