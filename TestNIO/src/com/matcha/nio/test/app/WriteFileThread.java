package com.matcha.nio.test.app;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by Matcha on 16/9/11.
 */
public class WriteFileThread implements Runnable
{
    private static final String[] strs2Write;

    static
    {
        strs2Write = new String[]{
            "LiSA",
            "蓝井艾露",
            "さユり",
            "ユリカ"
        };
    }

    private String filePath;
    private boolean lockHoleFile;
    private long position;
    private String str2Write;
    private byte[] strBytes2Write;
    private Charset charset;
    private File file2Write;

    public WriteFileThread(String filePath, boolean lockHoleFile, long position, Charset charset)
    {
        this(filePath, lockHoleFile, position, null, charset);
    }

    public WriteFileThread(String filePath, boolean lockHoleFile, long position, String str2Write, Charset charset)
    {
        this.filePath = filePath;
        this.lockHoleFile = lockHoleFile;
        this.position = position;
        this.str2Write = str2Write;
        this.charset = charset;
        this.file2Write = new File(filePath);
        if(str2Write == null || str2Write.isEmpty())
        {
            Random random = new Random();
            int index = random.nextInt(strs2Write.length);
            this.str2Write = strs2Write[index];
        }

        this.strBytes2Write = this.str2Write.getBytes(this.charset);
    }

    @Override
    public void run()
    {
        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(file2Write, "rw");
                FileChannel fileChannel = randomAccessFile.getChannel();
                FileLock fileLock = getFileLock(fileChannel)
        )
        {
            if(fileLock == null)
                throw new RuntimeException("can not get file lock!!!");
            long thePosition = fileLock.position();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(this.strBytes2Write.length);
            byteBuffer.put(this.strBytes2Write);
            byteBuffer.flip();
            fileChannel.write(byteBuffer, thePosition);
            System.out.println("write " + str2Write + " finish!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private FileLock getFileLock(FileChannel fileChannel) throws IOException
    {
        if(lockHoleFile)
            return fileChannel.tryLock();
        else
            return fileChannel.tryLock(position, strBytes2Write.length, false);
    }
}
