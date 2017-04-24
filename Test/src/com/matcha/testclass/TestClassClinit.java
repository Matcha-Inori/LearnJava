package com.matcha.testclass;

import com.matcha.testclass.app.ClassClinit;

/**
 * Created by Administrator on 2017/4/24.
 */
public class TestClassClinit
{
    public static void main(String[] args)
    {
        Runnable script = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println(Thread.currentThread() + " start");
                ClassClinit classClinit = new ClassClinit();
                System.out.println(Thread.currentThread() + " run over");
            }
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
