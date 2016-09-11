package com.matcha.nio.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/11.
 */
public class TestWriteFile
{
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args)
    {
        StringBuffer filePath = new StringBuffer();
        filePath.append("resource").append(File.separator).append("txt").append(File.separator).append("file2.txt");
        File file2Write = new File(filePath.toString());
        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(file2Write, "rw");
                FileChannel fileChannel = randomAccessFile.getChannel()
        )
        {
            long writePosition = 0;
//            String toWrite = "欧拉欧拉欧拉欧拉    木大木大木大木大  test write file";
            String toWrite = "插入这段文字";
            byte[] bytes2Write = toWrite.getBytes(charset);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes2Write.length);
            byteBuffer.put(bytes2Write);
            byteBuffer.flip();
            fileChannel.write(byteBuffer, writePosition);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
