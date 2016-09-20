package com.matcha.nio.test.app;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Created by Administrator on 2016/9/20.
 */
public class CloseThread extends IdedThread
{
    private Selector selector;

    public CloseThread(Selector selector)
    {
        this.selector = selector;
        this.setName("CloseThread - " + id);
    }

    @Override
    public void run()
    {
        try
        {
            selector.close();
            System.out.println("CloseThread - " + id + " close finish");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
