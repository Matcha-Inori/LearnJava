package com.matcha.nio.server.service;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2016/9/22.
 */
public class LoginService extends AbstractServices
{
    private static final String LOGIN_SERVICE = "LoginService";

    public LoginService()
    {
        super(LOGIN_SERVICE);
    }

    @Override
    protected void initService()
    {
        super.initService();
    }

    @Override
    public void service(SelectionKey selectionKey)
    {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);
    }
}
