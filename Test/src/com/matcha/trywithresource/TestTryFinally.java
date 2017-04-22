package com.matcha.trywithresource;

/**
 * Created by Administrator on 2017/4/20.
 */
public class TestTryFinally
{
    public static void main(String[] args)
    {
        try
        {
            test();
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    private static void test()
    {
        try
        {
            System.out.println("in test try");
            throw new RuntimeException("try");
        }
        finally
        {
            throw new RuntimeException("finally");
        }
    }
}
