package com.matcha.statictest;

import com.matcha.other.OneStaticClass;

/**
 * Created by rd_xidong_ren on 2016/7/13.
 */
public class StaticTest {

    public static void main(String[] args) {
//        try
//        {
//            Class.forName("com.matcha.other.OneStaticClass");
//        }
//        catch (ClassNotFoundException e)
//        {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
        int count = OneStaticClass.getCount();
        System.out.println(count);
        OneStaticClass oneStaticClass1 = new OneStaticClass();
        OneStaticClass twoStaticClass2 = new OneStaticClass();
        count = OneStaticClass.getCount();
        System.out.println(count);
    }

}