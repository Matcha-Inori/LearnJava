package com.matcha.nio.test;

import com.matcha.nio.test.app.WriteFileThread;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/11.
 */
public class TestFileLockWrite
{
    public static void main(String[] args)
    {
        Charset charset = Charset.forName("UTF-8");
        StringBuffer filePath = new StringBuffer();
        filePath.append("resource").append(File.separator).append("txt")
                .append(File.separator).append("testFileLock.txt");
        Thread writeFileThread = new Thread(new WriteFileThread(filePath.toString(), true, 10, charset),
                "WriteFileThread");
        writeFileThread.start();
    }
}
