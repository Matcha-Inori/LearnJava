package com.matcha.nio.test;

import com.matcha.nio.test.app.ReadFileThread;
import com.matcha.nio.test.app.WriteFileThread;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 获取位置重合的文件锁的时候,如果是不同的jvm,那么仅仅是获取不到锁,但是如果是同一个jvm,那么会抛出OverlappingFileLockException异常
 * Created by Matcha on 16/9/11.
 */
public class TestFileLock
{
    public static void main(String[] args)
    {
        Charset charset = Charset.forName("UTF-8");
        StringBuffer filePath = new StringBuffer();
        filePath.append("resource").append(File.separator).append("txt")
                .append(File.separator).append("testFileLock.txt");
        Thread writeFileThread = new Thread(new WriteFileThread(filePath.toString(), true, 10, charset),
                "WriteFileThread");
        Thread readFileThread = new Thread(new ReadFileThread(filePath.toString(), true, 30, 20, true, charset),
                "ReadFileThread");
        writeFileThread.start();
        readFileThread.start();
    }
}
