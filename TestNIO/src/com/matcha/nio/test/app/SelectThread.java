package com.matcha.nio.test.app;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Created by Administrator on 2016/9/20.
 */
public class SelectThread extends IdedThread
{
    private Selector selector;

    public SelectThread(Selector selector)
    {
        this.selector = selector;
        this.setName("SelectThread - " + id);
    }

    @Override
    public void run()
    {
        try
        {
            int selected = selector.select();
            System.out.println("SelectThread - " + id + " select finish - " + selected);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
