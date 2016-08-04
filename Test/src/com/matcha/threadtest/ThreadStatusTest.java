package com.matcha.threadtest;

/**
 * Created by rd_xidong_ren on 2016/7/14.
 */
public class ThreadStatusTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Object o  = new Object();
                    o.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }, "Mathca thread");
        System.out.println("thread1 create finished");
        thread1.start();
    }
}
