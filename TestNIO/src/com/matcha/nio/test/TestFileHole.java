package com.matcha.nio.test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Matcha on 16/9/10.
 */
public class TestFileHole
{
    public static void main(String[] args)
    {
        StringBuffer writeFilePath = new StringBuffer();
        writeFilePath.append("resource").append(File.separator).append("txt")
                .append(File.separator).append("holeFile.txt");
        File writeFile = new File(writeFilePath.toString());
        if(writeFile.exists())
            writeFile.delete();
        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(writeFilePath.toString(), "rw");
                FileChannel fileChannel = randomAccessFile.getChannel()
        )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(30);
            FileDescriptor fileDescriptor = randomAccessFile.getFD();
            System.out.println(fileDescriptor);
            write(0, byteBuffer, fileChannel);
            write(5000000, byteBuffer, fileChannel);
            write(50000, byteBuffer, fileChannel);
            System.out.println("the file size is " + randomAccessFile.length());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void write(long position,
                              ByteBuffer byteBuffer,
                              FileChannel fileChannel) throws IOException
    {
        byteBuffer.clear();
        StringBuffer str2Write = new StringBuffer();
        str2Write.append("<-------- ").append(position).append("\n");
        byteBuffer.put(str2Write.toString().getBytes("UTF-8"));
        byteBuffer.flip();
        fileChannel.position(position);
        fileChannel.write(byteBuffer);
    }
}
