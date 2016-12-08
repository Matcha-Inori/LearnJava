package com.matcha.net;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

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

            URI testURI = new URI("http", "www.baidu.com", "/abc/†©ß", "a=1&b=2", "abc");
            System.out.println(testURI);

            URL otherURL = new URL("http", "localhost", -1, "/abc");
            System.out.println(otherURL);

            URL otherURL2 = new URL(otherURL, "/a/c/b");
            System.out.println(otherURL2);

            URI specialURI = new URI("http", "†©®ƒ", "fragement");
            System.out.println(specialURI);

            URI specialURI2 = new URI("http:˜¥¨ƒ∂∂∂//www.baidu.com/abc/sh?name=1&ss=2#fff");
            System.out.println(specialURI2);
            System.out.println(specialURI2.toASCIIString());
            System.out.println("specialURI2.getRawAuthority() - " + specialURI2.getRawAuthority());
            System.out.println("specialURI2.getRawFragment() - " + specialURI2.getRawFragment());
            System.out.println("specialURI2.getRawPath() - " + specialURI2.getRawPath());
            System.out.println("specialURI2.getRawQuery() - " + specialURI2.getRawQuery());
            System.out.println("specialURI2.getRawSchemeSpecificPart() - " + specialURI2.getRawSchemeSpecificPart());

            URI specialURI3 = new URI(null, null, "a/b/c/d/f", "fragment");
            System.out.println(specialURI3);

            URI specialURI4 =
                    new URI("http://www.baidu.com/abc/%CB%9C%C2%A5%C2%A8%C6%92%E2%88%82%E2%88%82%E2%88%82?name=1&ss=2#fff");
            System.out.println(specialURI4);
            System.out.println(specialURI4.toASCIIString());
            System.out.println("specialURI4.getAuthority() - " + specialURI4.getAuthority());
            System.out.println("specialURI4.getFragment() - " + specialURI4.getFragment());
            System.out.println("specialURI4.getPath() - " + specialURI4.getPath());
            System.out.println("specialURI4.getQuery() - " + specialURI4.getQuery());
            System.out.println("specialURI4.getScheme() - " + specialURI4.getScheme());
            System.out.println("specialURI4.getSchemeSpecificPart() - " + specialURI4.getSchemeSpecificPart());

            System.out.println("specialURI4.getRawAuthority() - " + specialURI4.getRawAuthority());
            System.out.println("specialURI4.getRawFragment() - " + specialURI4.getRawFragment());
            System.out.println("specialURI4.getRawPath() - " + specialURI4.getRawPath());
            System.out.println("specialURI4.getRawQuery() - " + specialURI4.getRawQuery());
            System.out.println("specialURI4.getRawSchemeSpecificPart() - " + specialURI4.getRawSchemeSpecificPart());

            URL specialURL_4 = specialURI4.toURL();
            System.out.println(specialURL_4);

            URI specialURI5 = new URI("http://www.baidu.com/abc/˜¥¨ƒ∂∂∂?name=1&ss=2#fff");
            System.out.println(specialURI5);
            System.out.println(specialURI5.toASCIIString());
            System.out.println("specialURI5.getAuthority() - " + specialURI5.getAuthority());
            System.out.println("specialURI5.getFragment() - " + specialURI5.getFragment());
            System.out.println("specialURI5.getPath() - " + specialURI5.getPath());
            System.out.println("specialURI5.getQuery() - " + specialURI5.getQuery());
            System.out.println("specialURI5.getScheme() - " + specialURI5.getScheme());
            System.out.println("specialURI5.getSchemeSpecificPart() - " + specialURI5.getSchemeSpecificPart());

            System.out.println("specialURI5.getRawAuthority() - " + specialURI5.getRawAuthority());
            System.out.println("specialURI5.getRawFragment() - " + specialURI5.getRawFragment());
            System.out.println("specialURI5.getRawPath() - " + specialURI5.getRawPath());
            System.out.println("specialURI5.getRawQuery() - " + specialURI5.getRawQuery());
            System.out.println("specialURI5.getRawSchemeSpecificPart() - " + specialURI5.getRawSchemeSpecificPart());

            URL specialURL_5 = specialURI5.toURL();
            System.out.println(specialURL_5);

            //exception : java.lang.IllegalArgumentException: URI is not absolute
//            URL specialURL = specialURI3.toURL();
//            System.out.println(specialURL);

            //exception
//            URL specialURL = new URL(null, null, -1, "a/b/c/d/f");
//            System.out.println(specialURL);

            System.out.println("===========");

            URL url1 = new URL("http", "www.baidu.com", -1, "/a/b/c/d?a=1");
            System.out.println(url1.getFile());
            System.out.println(url1.getQuery());
            URI uri1 = url1.toURI();
            System.out.println(uri1.toASCIIString());
            System.out.println(uri1.getAuthority());
            System.out.println(uri1.getPath());
        }
        catch (MalformedURLException | URISyntaxException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection = null;
        try
        {
            urlConnection = (HttpURLConnection) baiduURL.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.connect();
            int contentLength = urlConnection.getContentLength();
            try(
                    InputStream inputStream = urlConnection.getInputStream();
                    ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream)
            )
            {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(contentLength);
                byte[] strByte = new byte[contentLength];
                readableByteChannel.read(byteBuffer);
                byteBuffer.flip();
                byteBuffer.get(strByte);
                System.out.println(new String(strByte));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }
}
