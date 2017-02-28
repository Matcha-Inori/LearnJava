package com.matcha.classloader.thread;

import com.matcha.classloader.test.ITest;
import com.matcha.classloader.test.TestImpl;

/**
 * Created by Administrator on 2017/2/28.
 */
public class TestRunnable2 implements Runnable
{
    @Override
    public void run()
    {
        ITest iTest = new TestImpl("[a-zA-Z]+");
        System.out.println(iTest.test(10, "Riven"));
    }
}
