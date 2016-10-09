package com.matcha.io.app;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/10/9.
 */
public class FileChanelReadTest extends ReadTest
{
    private static final String selfTestName = "FileChanel Read Test";

    public FileChanelReadTest(int count,
                                 String filePath,
                                 int size)
    {
        super(selfTestName, count, filePath, size, 0);
    }

    @Override
    protected long read()
    {
        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
                FileChannel fileChannel = randomAccessFile.getChannel()
        )
        {
            byte[] readBytes = new byte[size];
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size);
            int allCount = 0;
            long stratTime = System.nanoTime();

            for(int readCount = 0;(readCount = fileChannel.read(byteBuffer)) > 0;)
            {
                byteBuffer.flip();
                byteBuffer.get(readBytes);
                allCount += readCount;
                byteBuffer.clear();
            }
            long endTime = System.nanoTime();
            System.out.println("file chanel read finish! cost - "
                    + (endTime - stratTime) + " and allCount - " + allCount);
            return endTime - stratTime;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
