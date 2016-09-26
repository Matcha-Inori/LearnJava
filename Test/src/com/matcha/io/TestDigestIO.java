package com.matcha.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Matcha on 16/9/26.
 */
public class TestDigestIO
{
    public static void main(String[] args)
    {
        byte[] writeBytes = null;
        byte[] readBytes = null;

        byte[] hashWrite = null;
        byte[] hashRead = null;

        MessageDigest messageDigestWrite = null;
        MessageDigest messageDigestRead = null;
        try
        {
            messageDigestWrite = MessageDigest.getInstance("sha");
            messageDigestRead = MessageDigest.getInstance("sha");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DigestOutputStream digestOutputStream =
                        new DigestOutputStream(byteArrayOutputStream, messageDigestWrite)
        )
        {
            digestOutputStream.write("今天也是个好天气!!!".getBytes(Charset.forName("UTF-8")));
            hashWrite = messageDigestWrite.digest();
            writeBytes = byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(writeBytes);
                DigestInputStream digestInputStream = new DigestInputStream(byteArrayInputStream, messageDigestRead)
        )
        {
            int availableSize = digestInputStream.available();
            readBytes = new byte[availableSize];
            digestInputStream.read(readBytes);
            hashRead = messageDigestRead.digest();
            String printStr = new String(readBytes, Charset.forName("UTF-8"));
            System.out.println(printStr);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println(messageDigestRead.equals(messageDigestWrite));
        System.out.println(Arrays.equals(hashWrite, hashRead));
    }
}
