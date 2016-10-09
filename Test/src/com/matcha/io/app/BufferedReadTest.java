package com.matcha.io.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/10/9.
 */
public class BufferedReadTest extends ReadTest
{
    private static final String selfTestName = "Buffered Read Test";

    public BufferedReadTest(int count,
                               String filePath,
                               int size,
                               int bufferedSize)
    {
        super(selfTestName, count, filePath, size, bufferedSize);
    }

    @Override
    protected long read()
    {
        try (
                FileInputStream fileInputStream = new FileInputStream(filePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, bufferedSize)
        )
        {
            byte[] readBytes = new byte[size];
            int allCount = 0;
            System.out.println("buffered read start!");
            long stratTime = System.nanoTime();
            for(int size = 0;(size = bufferedInputStream.read(readBytes)) > 0;)
                allCount += size;
            long endTime = System.nanoTime();
            System.out.println("buffered read finish! cost - " + (endTime - stratTime) + " and allCount - " + allCount);
            return endTime - stratTime;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
