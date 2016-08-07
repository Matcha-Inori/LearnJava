package com.matcha.trywithresource;

import com.matcha.trywithresource.app.OneCloseableClass;
import jdk.jfr.events.ThrowablesEvent;

import java.io.IOException;

/**
 * Created by Matcha on 16/8/7.
 */
public class TestTryIn170
{
    public static void main(String[] args)
    {
        try
        {
            test1();
        }
        catch (IOException e)
        {
            //这个地方是可以拿到多个异常的
            e.printStackTrace();
            Throwable[] throwables = e.getSuppressed();
            for(Throwable throwable : throwables)
            {
                throwable.printStackTrace();
            }
        }
    }

    public static void test1() throws IOException
    {
        try(
                OneCloseableClass oneCloseableClass = new OneCloseableClass();
                OneCloseableClass otherCloseableClass = new OneCloseableClass()
        )
        {
            oneCloseableClass.print();
            otherCloseableClass.print();
            throw new IOException("Test");
        }
        //如果加上这个那么在关闭资源的时候抛出的异常是可以捕捉到的,但是如果是在finally里面关闭
        //那么异常必然会抛出
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        System.out.println("not found exception");
    }
}
