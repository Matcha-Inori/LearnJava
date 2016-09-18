package com.matcha.nio.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.WritableByteChannel;

/**
 * Created by Matcha on 16/9/18.
 */
public class TestPipe
{
    private static final String[] strs;

    static
    {
        strs = new String[]{
                "#测试#",
                "†LiSA†",
                "√蓝井艾露√",
                "∫ユリカ∫",
                "πさユりπ"
        };
    }

    public static void main(String[] args)
    {
        Pipe pipe = null;
        try
        {
            pipe = Pipe.open();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try(
                WritableByteChannel writableByteChannel = Channels.newChannel(System.out);
                Pipe.SinkChannel sinkChannel = pipe.sink();
                Pipe.SourceChannel sourceChannel = pipe.source()
        )
        {
            Thread otherThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        ByteBuffer writeByteBuffer = ByteBuffer.allocateDirect(128);
                        for(int i = 0;i < strs.length;i++)
                        {
                            writeByteBuffer.put(strs[i].getBytes());
                            writeByteBuffer.flip();
                            sinkChannel.write(writeByteBuffer);
                            writeByteBuffer.clear();
                        }
                        sinkChannel.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }, "OtherThead");
            otherThread.start();
            ByteBuffer readByteBuffer = ByteBuffer.allocateDirect(128);
            while(0 <= sourceChannel.read(readByteBuffer))
            {
                readByteBuffer.flip();
                writableByteChannel.write(readByteBuffer);
                readByteBuffer.clear();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
