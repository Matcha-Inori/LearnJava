package com.matcha.semaphore.exception;

/**
 * Created by Administrator on 2016/8/8.
 */
public class NoConnectionException extends Exception
{
    private static final String info;

    static
    {
        info = "THERE IS NO CONNECTION CAN BE USE!";
    }

    public NoConnectionException()
    {
        super(info);
    }
}
