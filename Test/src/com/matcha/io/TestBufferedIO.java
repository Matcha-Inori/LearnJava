package com.matcha.io;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/9/29.
 */
public class TestBufferedIO
{
    public static void main(String[] args)
    {
        String filePath = "resource" + File.separator + "txt" + File.separator + "bigFile.txt";

        preparedFile(filePath);

        try (
                FileInputStream fileInputStream = new FileInputStream(filePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 1024)
        )
        {
            byte[] readBytes = new byte[500];
            int allCount = 0;
            System.out.println("buffered read start!");
            long stratTime = System.nanoTime();
            for(int size = 0;(size = bufferedInputStream.read(readBytes)) > 0;)
            {
                allCount += size;
//                System.out.println("read - " + size + " and all count is " + allCount);
            }
            long endTime = System.nanoTime();
            System.out.println("buffered read finish! cost - " + (endTime - stratTime) + " and allCount - " + allCount);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println("");

        try(FileInputStream fileInputStream = new FileInputStream(filePath))
        {
            byte[] readBytes = new byte[500];
            int allCount = 0;
            System.out.println("normal read start!");
            long stratTime = System.nanoTime();
            for(int size = 0;(size = fileInputStream.read(readBytes)) > 0;)
            {
                allCount += size;
//                System.out.println("read - " + size + " and all count is " + allCount);
            }
            long endTime = System.nanoTime();
            System.out.println("normal read finish! cost - " + (endTime - stratTime) + " and allCount - " + allCount);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println("");

        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
                FileChannel fileChannel = randomAccessFile.getChannel()
        )
        {
            MappedByteBuffer mappedByteBuffer =
                    fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            byte[] readBytes = new byte[500];
            int allCount = 0;
            System.out.println("mapped buffer read start!");
            long stratTime = System.nanoTime();
            while(mappedByteBuffer.hasRemaining())
            {
                int readCount = Math.min(500, mappedByteBuffer.remaining());
                mappedByteBuffer.get(readBytes);
                allCount += readCount;
            }
            long endTime = System.nanoTime();
            System.out.println("mapped buffer read finish! cost - "
                    + (endTime - stratTime) + " and allCount - " + allCount);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void preparedFile(String filePath)
    {
        File file = new File(filePath);
        if (file.exists() && file.isFile())
            file.delete();

        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
                FileChannel fileChannel = randomAccessFile.getChannel()
        )
        {
            MappedByteBuffer mappedByteBuffer =
                    fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 500 * 1024 * 1024);
            for(int remaining = 0;(remaining = mappedByteBuffer.remaining()) > 0;)
            {
                int size = Math.min(1024, remaining);
                byte[] writeBytes = new byte[size];
                for(int index = 0;index < size;index++)
                {
                    writeBytes[index] = (byte) index;
                }
                mappedByteBuffer.put(writeBytes);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
