package com.matcha.nio.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by Administrator on 2016/9/12.
 */
public class TestChannel2Channel
{
    public static void main(String[] args)
    {
        StringBuffer filePath = new StringBuffer();
        filePath.append("resource").append(File.separator).append("txt")
                .append(File.separator).append("file1.txt");
        File file2Transfer = new File(filePath.toString());
        if(!file2Transfer.exists())
            throw new RuntimeException("the file1 is not exists!!");
        try(
                FileInputStream fileInputStream = new FileInputStream(file2Transfer);
                FileChannel fileChannel = fileInputStream.getChannel();
                WritableByteChannel writableByteChannel = Channels.newChannel(System.out)
        )
        {
            fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
