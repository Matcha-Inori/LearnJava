package com.matcha.nio.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/9/12.
 */
public class TestMappedFile
{
    public static void main(String[] args)
    {
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        File tempFile = prepareTmpFile();
        try(
                RandomAccessFile tempRandomAccessFile = new RandomAccessFile(tempFile, "rw");
                FileChannel tempFileChannel = tempRandomAccessFile.getChannel()
        )
        {
            MappedByteBuffer readOnlyBuffer =
                    tempFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, tempFileChannel.size());
            MappedByteBuffer readWriteBuffer =
                    tempFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, tempFileChannel.size());
            MappedByteBuffer privateBuffer =
                    tempFileChannel.map(FileChannel.MapMode.PRIVATE, 0, tempFileChannel.size());
            System.out.println("begin!!!");
            showBuffers(readOnlyBuffer, readWriteBuffer, privateBuffer);

            privateBuffer.position(8);
            privateBuffer.put("COW".getBytes());
            System.out.println("Change to privateBuffer buffer");
            showBuffers(readOnlyBuffer, readWriteBuffer, privateBuffer);

            readWriteBuffer.position(9);
            readWriteBuffer.put(" R/W ".getBytes());
            readWriteBuffer.position(8194);
            readWriteBuffer.put(" R/W ".getBytes());
            readWriteBuffer.force();
            System.out.println("Change to readWriteBuffer buffer");
            showBuffers(readOnlyBuffer, readWriteBuffer, privateBuffer);

            ByteBuffer tmpBuffer = ByteBuffer.allocateDirect(100);
            tmpBuffer.put("Channel write ".getBytes());
            tmpBuffer.flip();
            tempFileChannel.write(tmpBuffer);
            tmpBuffer.rewind();
            tempFileChannel.write(tmpBuffer, 8202);
            System.out.println("Write on channel");
            showBuffers(readOnlyBuffer, readWriteBuffer, privateBuffer);

            privateBuffer.position(8207);
            privateBuffer.put(" COW2 ".getBytes());
            System.out.println("Second change to privateBuffer buffer");
            showBuffers(readOnlyBuffer, readWriteBuffer, privateBuffer);

            readWriteBuffer.position(0);
            readWriteBuffer.put(" R/W2 ".getBytes());
            readWriteBuffer.position(8210);
            readWriteBuffer.put(" R/W2 ".getBytes());
            readWriteBuffer.force();
            System.out.println("Second change to R/W buffer");
            showBuffers(readOnlyBuffer, readWriteBuffer, privateBuffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            tempFile.delete();
        }
    }

    private static void showBuffers(MappedByteBuffer readOnlyBuffer,
                                    MappedByteBuffer readWriteBuffer,
                                    MappedByteBuffer privateBuffer)
    {
        dumpBuffer("readOnlyBuffer", readOnlyBuffer);
        dumpBuffer("readWriteBuffer", readWriteBuffer);
        dumpBuffer("privateBuffer", privateBuffer);
    }

    private static void dumpBuffer(String prefix, ByteBuffer byteBuffer)
    {
        System.out.print(prefix + ": '");
        int nulls = 0;
        int limit = byteBuffer.limit();
        char c;
        for(int i = 0;i < limit;i++)
        {
//            c = byteBuffer.getChar(i);
            c = (char) byteBuffer.get(i);
            if(c == '\u0000')
            {
                nulls++;
                continue;
            }
            if(nulls != 0)
            {
                System.out.print("|[" + nulls + " nulls]|");
                nulls = 0;
            }
            System.out.print(c);
        }
        System.out.println("'");
    }

    private static File prepareTmpFile()
    {
        File tempFile;
        try
        {
            tempFile = File.createTempFile("TestMappedFile", ".txt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "rw");
                FileChannel tmpFileChannel = randomAccessFile.getChannel()
        )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
            byteBuffer.put("This is the file content".getBytes());
            byteBuffer.flip();
            tmpFileChannel.write(byteBuffer);
            byteBuffer.clear();
            byteBuffer.put("This is more file content".getBytes());
            byteBuffer.flip();
            tmpFileChannel.write(byteBuffer, 8192);
            return tempFile;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
