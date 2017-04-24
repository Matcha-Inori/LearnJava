package com.matcha.classloader;

import com.matcha.classloader.test.ITest;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2017/2/28.
 */
public class TestClassLoader2
{
    public static void main(String[] args)
    {
        try
        {
            OwnClassLoader classLoader = new OwnClassLoader();
            Class<?> testClass = classLoader.loadClass("com.matcha.classloader.test.TestImpl");
            Constructor<?> testClassConstructor = testClass.getConstructor(String.class);
            //这里会抛出ClassCastException
            ITest iTest = (ITest) testClassConstructor.newInstance("[a-zA-Z]+");
            iTest.test(10, "Riven");
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException |
                NoSuchMethodException |
                IllegalAccessException |
                InstantiationException |
                InvocationTargetException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try
        {
            OwnClassLoader classLoader = new OwnClassLoader();
            Class<?> threadClass = classLoader.loadClass("com.matcha.classloader.thread.TestRunnable2");
            Runnable runnable = (Runnable) threadClass.newInstance();
            Thread thread1 = new Thread(runnable, "thread1");
            thread1.setContextClassLoader(classLoader);
            thread1.start();
        }
        catch (ClassNotFoundException |
                IllegalAccessException |
                InstantiationException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
