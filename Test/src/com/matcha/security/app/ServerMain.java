package com.matcha.security.app;

/**
 * Created by Matcha on 2016/12/14.
 */
public class ServerMain
{
    public static void main(String[] args)
    {
        Server server = new Server(8668);
        server.start();
    }
}
