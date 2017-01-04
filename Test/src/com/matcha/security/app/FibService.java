package com.matcha.security.app;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by Matcha on 2016/12/14.
 */
public class FibService
{
    private static volatile FibService instance = null;

    public static FibService getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new FibService();
    }

    private FibService()
    {

    }

    public int fib(int argInt)
    {
        if(argInt < 0)
            throw new IllegalArgumentException("argument argInt must bigger than zero");

        if(argInt <= 1)
            return argInt;
        else
            return fib(argInt - 1) + fib(argInt - 2);
    }

    public int fib(int argInt, AccessControlContext argCtx)
    {
        return AccessController.doPrivileged(new PrivilegedAction<Integer>()
        {
            @Override
            public Integer run()
            {
                AccessControlContext accessControlContext = AccessController.getContext();
                System.out.println("accessControlContext - " + accessControlContext);
                if(argInt < 0)
                    throw new IllegalArgumentException("argument argInt must bigger than zero");

                if(argInt <= 1)
                    return argInt;
                else
                    return fib(argInt - 1) + fib(argInt - 2);
            }
        }, argCtx);
    }
}
