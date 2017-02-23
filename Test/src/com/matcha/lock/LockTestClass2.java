package com.matcha.lock;

import com.matcha.lock.factory.DefaultThreadFacotry;
import com.matcha.lock.factory.IThreadFactory;

/**
 * Created by rd_xidong_ren on 2016/7/21.
 */
public class LockTestClass2
{
    public static void main(String[] args)
    {
        int count = 8;
        Thread thread = null;
        IThreadFactory iThreadFactory = DefaultThreadFacotry.getInstance();
        for(int i = 0;i < count;i++)
        {
            thread = iThreadFactory.newThread();
            thread.start();
        }
        iThreadFactory.startAllThread();
    }
}
