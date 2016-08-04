package com.matcha.other;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rd_xidong_ren on 2016/7/13.
 */
public class OneStaticClass {

    private static volatile AtomicInteger count;

    static
    {
        System.out.println("OneStaticClass ! static start");
        count = new AtomicInteger(0);
        System.out.println("OneStaticClass ! static finish");
    }

    public OneStaticClass() {
        count.addAndGet(1);
    }

    public static int getCount()
    {
        return count.get();
    }

}