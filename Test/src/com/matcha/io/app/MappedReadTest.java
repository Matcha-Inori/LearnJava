package com.matcha.io.app;

import sun.nio.ch.FileChannelImpl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/10/9.
 */
public class MappedReadTest extends ReadTest
{
    private static final String selfTestName = "Mapped Read Test";

    private boolean needLoad;

    public MappedReadTest(int count,
                          String filePath,
                          int size,
                          boolean needLoad)
    {
        super("mapped read test", count, filePath, size, 0);
        this.needLoad = needLoad;
    }

    @Override
    protected long read()
    {
        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
                FileChannel fileChannel = randomAccessFile.getChannel()
        )
        {
            MappedByteBuffer mappedByteBuffer =
                    fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            byte[] readBytes = new byte[size];
            int allCount = 0;
            long stratTime = System.nanoTime();
            if(needLoad)
                mappedByteBuffer.load();
            while(mappedByteBuffer.hasRemaining())
            {
                int readCount = Math.min(500, mappedByteBuffer.remaining());
                mappedByteBuffer.get(readBytes);
                allCount += readCount;
            }
            long endTime = System.nanoTime();
            Method unmapMethod = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
            unmapMethod.setAccessible(true);
            unmapMethod.invoke(fileChannel, mappedByteBuffer);
            System.out.println("mapped buffer read finish! cost - "
                    + (endTime - stratTime) + " and allCount - " + allCount);
            return endTime - stratTime;
        }
        catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
