package com.matcha.io.app;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/10/9.
 */
public class FileReadTest extends ReadTest
{
    private static final String selfTestName = "File Read Test";

    public FileReadTest(int count,
                           String filePath,
                           int size)
    {
        super(selfTestName, count, filePath, size, 0);
    }

    @Override
    protected long read()
    {
        try(FileInputStream fileInputStream = new FileInputStream(filePath))
        {
            byte[] readBytes = new byte[500];
            int allCount = 0;
            System.out.println("normal read start!");
            long stratTime = System.nanoTime();
            for(int size = 0;(size = fileInputStream.read(readBytes)) > 0;)
                allCount += size;
            long endTime = System.nanoTime();
            System.out.println("normal read finish! cost - " + (endTime - stratTime) + " and allCount - " + allCount);
            return endTime - stratTime;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
