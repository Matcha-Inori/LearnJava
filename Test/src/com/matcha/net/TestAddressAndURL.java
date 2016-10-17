package com.matcha.net;

import java.io.*;
import java.net.*;
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
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String str = null;
        try
        {
            urlConnection = (HttpURLConnection) baiduURL.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            bufferedReader = new BufferedReader(inputStreamReader);
            while((str = bufferedReader.readLine()) != null)
                System.out.println(str);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
                urlConnection.disconnect();

            try
            {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (inputStreamReader != null)
                    inputStreamReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (bufferedReader != null)
                    bufferedReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
