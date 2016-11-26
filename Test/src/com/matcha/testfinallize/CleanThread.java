package com.matcha.testfinallize;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Created by Administrator on 2016/11/23.
 */
public class CleanThread implements Runnable
{
    private ReferenceQueue<OtherObject> referenceQueue;

    public CleanThread(ReferenceQueue<OtherObject> referenceQueue)
    {
        this.referenceQueue = referenceQueue;
    }

    @Override
    public void run()
    {
        Reference<? extends OtherObject> reference = null;
        while(!Thread.interrupted())
        {
            reference = referenceQueue.poll();
            if(reference == null)
                continue;

            if(reference.get() == null)
                System.out.println("reference - " + reference + " has cleaned!");
            else
                System.out.println("reference - " + reference.get() + " has not cleaned!");
        }
    }
}
