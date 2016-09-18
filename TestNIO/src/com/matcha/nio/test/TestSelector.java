package com.matcha.nio.test;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Created by Matcha on 16/9/19.
 */
public class TestSelector
{
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
                Selector selector = Selector.open();
                Pipe.SinkChannel sinkChannel = pipe.sink();
                Pipe.SourceChannel sourceChannel = pipe.source()
        )
        {
            SelectionKey selectionKey = sinkChannel.register(selector, SelectionKey.OP_WRITE);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
