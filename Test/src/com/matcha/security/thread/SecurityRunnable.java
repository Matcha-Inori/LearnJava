package com.matcha.security.thread;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 2016/12/13.
 */
public class SecurityRunnable implements Runnable
{
    private static AtomicInteger count;

    static
    {
        count = new AtomicInteger(2);
    }

    @Override
    public void run()
    {
        Thread currentThread = Thread.currentThread();
        AccessControlContext accessControlContext = AccessController.getContext();
        System.out.println(currentThread.getName() + " : " + accessControlContext);
        int tmpCount = count.addAndGet(-1);
        if(tmpCount > 0)
            new Thread(new SecurityRunnable(), "Security-" + tmpCount).start();
    }
}
