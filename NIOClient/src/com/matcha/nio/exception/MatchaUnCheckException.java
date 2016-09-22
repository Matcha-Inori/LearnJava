package com.matcha.nio.exception;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MatchaUnCheckException extends RuntimeException
{
    public MatchaUnCheckException()
    {
    }

    public MatchaUnCheckException(String message)
    {
        super(message);
    }

    public MatchaUnCheckException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MatchaUnCheckException(Throwable cause)
    {
        super(cause);
    }

    public MatchaUnCheckException(String message,
                                  Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
