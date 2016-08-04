package com.matcha.lock.factory;

/**
 * Created by rd_xidong_ren on 2016/7/25.
 */
public interface IReadWriteThreadFacotry extends IThreadFactory
{
    @Override
    default Thread newThread()
    {
        return newGetThread();
    }

    public Thread newGetThread();
    public Thread newPutThread();
}
