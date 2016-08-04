package com.matcha.lock.factory;

/**
 * Created by rd_xidong_ren on 2016/7/21.
 */
public interface IThreadFactory
{
    public Thread newThread();
    public void startAllThread();
}
