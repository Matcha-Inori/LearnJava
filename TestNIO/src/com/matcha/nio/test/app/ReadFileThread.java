package com.matcha.nio.test.app;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;

/**
 * Created by Matcha on 16/9/11.
 */
public class ReadFileThread implements Runnable
{
    private String filePath;
    private boolean part;
    private long position;
    private long size;
    private boolean share;
    private Charset charset;
    private File file2Read;

    public ReadFileThread(String filePath)
    {
        this(filePath, false);
    }

    public ReadFileThread(String filePath, boolean share)
    {
        this(filePath, false, 0L, 0L, share);
    }

    public ReadFileThread(String filePath, boolean part, long position, long size, boolean share)
    {
        this(filePath, part, position, size, share, Charset.forName("UTF-8"));
    }

    public ReadFileThread(String filePath, boolean part, long position, long size, boolean share, Charset charset)
    {
        this.filePath = filePath;
        this.part = part;
        this.position = position;
        this.size = size;
        this.share = share;
        this.charset = charset;
        this.file2Read = new File(filePath);
        if(!file2Read.exists())
            throw new IllegalArgumentException("file is not exists!");
    }

    @Override
    public void run()
    {
        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(file2Read, "r");
                FileChannel fileChannel = randomAccessFile.getChannel();
                FileLock fileLock = getFileLock(fileChannel)
        )
        {
            if(fileLock == null)
                throw new RuntimeException("can not get file lock!!!");
            long thePosition = fileLock.position();
            long theSize = fileLock.size();
            byte[] bytes = new byte[(int) theSize];
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) theSize);
            fileChannel.read(byteBuffer, thePosition);
            byteBuffer.flip();
            byteBuffer.get(bytes);
            String str = new String(bytes, charset);
            System.out.println("the file str is: ");
            System.out.println(str);
            System.out.println();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private FileLock getFileLock(FileChannel fileChannel) throws IOException
    {
        if(part)
            return fileChannel.tryLock(position, size, share);
        else
            return fileChannel.tryLock(0, fileChannel.size(), share);
    }
}
