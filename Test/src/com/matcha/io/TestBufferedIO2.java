package com.matcha.io;

import com.matcha.io.app.*;

import java.io.File;

/**
 * Created by Administrator on 2016/10/9.
 */
public class TestBufferedIO2
{
    public static void main(String[] args)
    {
        String filePath = "resource" + File.separator + "txt" + File.separator + "bigFile.txt";
        int count = 5;
        int size = 500;
        int bufferedSize = 1024;
        ReadTest readTest;
//        readTest = new BufferedReadTest(count, filePath, size, bufferedSize);
        readTest = new MappedReadTest(count,filePath, size, false);
//        readTest = new FileChanelReadTest(count, filePath, size);
//        readTest = new FileReadTest(count, filePath, size);
        ReadTestResult readTestResult = readTest.startTest();
        readTestResult.printResult();
    }
}
