package com.matcha.io.app;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/9/30.
 */
public abstract class ReadTest
{
    private int count;
    protected String testName;
    protected String filePath;
    protected int size;
    protected int bufferedSize;

    protected abstract int read();

    protected ReadTest(String testName,
                       int count,
                       String filePath,
                       int size,
                       int bufferedSize)
    {
        this.testName = testName;
        this.count = count;
        this.filePath = filePath;
        this.size = size;
        this.bufferedSize = bufferedSize;
    }

    final public ReadTestResult startTest()
    {
        preparedFile();
        ReadTestResult readTestResult = new ReadTestResult(testName);
        for(int i = 0;i < count;i++)
        {
            readTestResult.addTime(read());
        }
        return readTestResult;
    }

    private void preparedFile()
    {
        File file = new File(filePath);
        if (file.exists() && file.isFile())
            return;

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
                    writeBytes[index] = (byte) index;
                mappedByteBuffer.put(writeBytes);
            }
            file.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getTestName()
    {
        return testName;
    }

    public void setTestName(String testName)
    {
        this.testName = testName;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getBufferedSize()
    {
        return bufferedSize;
    }

    public void setBufferedSize(int bufferedSize)
    {
        this.bufferedSize = bufferedSize;
    }
}
