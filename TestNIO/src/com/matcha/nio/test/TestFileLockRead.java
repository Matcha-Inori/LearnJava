package com.matcha.nio.test;

import com.matcha.nio.test.app.ReadFileThread;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/11.
 */
public class TestFileLockRead
{
    public static void main(String[] args)
    {
        Charset charset = Charset.forName("UTF-8");
        StringBuffer filePath = new StringBuffer();
        filePath.append("resource").append(File.separator).append("txt")
                .append(File.separator).append("testFileLock.txt");
        Thread readFileThread = new Thread(new ReadFileThread(filePath.toString(), true, 30, 20, true, charset),
                "ReadFileThread");
        readFileThread.start();
    }
}
