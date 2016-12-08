package com.matcha.classloader.thread;

import com.matcha.classloader.app.A;

/**
 * Created by Matcha on 2016/11/28.
 */
public class TestRunnable implements Runnable
{
    @Override
    public void run()
    {
        A a = new A();
        a.print();
    }
}
