package com.matcha.threadtest;

/**
 * Created by rd_xidong_ren on 2016/7/15.
 */
public class ThreadJoinTest {

    public static void main(String[] args) {
        try
        {
            final Thread a = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        System.out.println("111");
                        while(true)
                        {
                            Thread.sleep(100);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            });

            Thread b = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        a.start();
                        while(true)
                        {
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            });
            b.start();
            Thread.sleep(1000);

            synchronized (a)
            {
                System.out.println("啦啦啦啦啦啦");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
