package com.matcha.net;

import java.io.IOException;
import java.net.*;

/**
 * Created by Matcha on 16/10/12.
 */
public class TestAddressAndURL
{
    public static void main(String[] args)
    {
        URL baiduURL = null;
        try
        {
            baiduURL = new URL("http", "www.baidu.com", "");
            System.out.println(baiduURL);
            System.out.println(baiduURL.toExternalForm());
            URI baiduURI = baiduURL.toURI();
            System.out.println(baiduURI);
        }
        catch (MalformedURLException | URISyntaxException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try
        {
            URLConnection urlConnection = baiduURL.openConnection();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
