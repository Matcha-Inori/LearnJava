package com.matcha.classloader;

import java.io.IOException;

/**
 * Created by Matcha on 2016/11/28.
 */
public class TestClassLoader
{
    public static void main(String[] args)
    {
        /*Thread thread1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    A a = new A();
                    a.print();
                    Class.forName("com.matcha.classloader.app.A");
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }, "thread1");
        thread1.setContextClassLoader(new OwnClassLoader2());
        thread1.start();*/

        try
        {
            OwnClassLoader classLoader = new OwnClassLoader();
            Class<?> thread = classLoader.loadClass("com.matcha.classloader.thread.TestRunnable");
            Runnable runnable = (Runnable) thread.newInstance();
            Thread thread1 = new Thread(runnable, "thread1");
            thread1.setContextClassLoader(classLoader);
            thread1.start();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
