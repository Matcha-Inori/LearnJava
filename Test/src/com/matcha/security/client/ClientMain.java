package com.matcha.security.client;

import java.io.*;

/**
 * Created by Matcha on 2016/12/14.
 */
public class ClientMain
{
    public static void main(String[] args)
    {
        Client client = new Client(8668);
        client.startClient();
    }
}