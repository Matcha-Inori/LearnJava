package com.matcha.nio.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Matcha on 16/9/9.
 */
public class TestFileChannel
{
    public static void main(String[] args)
    {
        StringBuffer filePath = new StringBuffer();
        filePath.append("resource").append(File.separator).append("txt").append(File.separator).append("file1.txt");
        File readFile = new File(filePath.toString());
        try(
                FileInputStream fileInputStream = new FileInputStream(readFile);
                FileChannel fileChannel = fileInputStream.getChannel()
                )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20);
            List<byte[]> byteList = new ArrayList<>();
            byte[] bytes;
            int allBytes = 0;
            int remaining = 0;
            while(fileChannel.read(byteBuffer) != -1)
            {
                byteBuffer.flip();
                remaining = byteBuffer.remaining();
                allBytes += remaining;
                bytes = new byte[remaining];
                byteBuffer.get(bytes);
                byteList.add(bytes);
                byteBuffer.clear();
            }
            bytes = new byte[allBytes];
            Iterator<byte[]> byteIter = byteList.iterator();
            byte[] oneBytes;
            allBytes = 0;
            while(byteIter.hasNext())
            {
                oneBytes = byteIter.next();
                System.arraycopy(oneBytes, 0, bytes, allBytes, oneBytes.length);
                allBytes += oneBytes.length;
            }
            String str = new String(bytes, "UTF-8");
            System.out.println(str);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
