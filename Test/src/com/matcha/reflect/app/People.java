package com.matcha.reflect.app;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/4/24.
 */
public class People
{
    private static final AtomicLong count;

    static
    {
        count = new AtomicLong(0);
    }

    private String name;
    private int age;

    public static long getCount()
    {
        return count.get();
    }

    public People(String name, int age)
    {
        this.name = name;
        this.age = age;
        count.incrementAndGet();
    }

    public void sing(String song)
    {
        System.out.println("person " + this + " is singing " + song);
    }

    private void sleep() throws InterruptedException
    {
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
