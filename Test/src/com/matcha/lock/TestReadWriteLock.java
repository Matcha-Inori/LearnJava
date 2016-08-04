package com.matcha.lock;

import com.matcha.lock.factory.IReadWriteThreadFacotry;
import com.matcha.lock.factory.ReadWriteThreadFacotry;

/**
 * Created by rd_xidong_ren on 2016/7/22.
 */
public class TestReadWriteLock
{
    public static void main(String[] args)
    {
        try
        {
            int getThreadCount = 3;
            int putThreadCount = 3;
            IReadWriteThreadFacotry iReadWriteThreadFacotry =
                    ReadWriteThreadFacotry.getInstance();
            for(int i = 0;i < getThreadCount;i++)
            {
                iReadWriteThreadFacotry.newGetThread();
            }
            for(int i = 0;i < putThreadCount;i++)
            {
                iReadWriteThreadFacotry.newPutThread();
            }
            Thread.sleep(100);
            iReadWriteThreadFacotry.startAllThread();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
