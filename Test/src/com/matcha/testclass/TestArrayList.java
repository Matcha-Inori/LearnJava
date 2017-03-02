package com.matcha.testclass;

/**
 * Created by Administrator on 2017/3/2.
 */
public class TestArrayList
{
    public static void main(String[] args)
    {
        System.out.println(create(int.class, 1));
        System.out.println(create(Integer.class, 1));
    }

    private static <T> T create(Class<T> theClass, T instance)
    {
        System.out.println(theClass == instance.getClass());
        return instance;
    }
}
