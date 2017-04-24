package com.matcha.testclass.app;

/**
 * Created by Administrator on 2017/4/24.
 */
public class ClassClinit
{
    static
    {
        if(true)
        {
            System.out.println(Thread.currentThread() + " init DeadLoopClass");
            while(true)
            {

            }
        }
    }
}
