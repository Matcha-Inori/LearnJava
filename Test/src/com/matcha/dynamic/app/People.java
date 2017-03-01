package com.matcha.dynamic.app;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/3/1.
 */
public class People
{
    private static final AtomicLong nextId;

    static
    {
        nextId = new AtomicLong(0);
    }

    public static long getPeopleCount()
    {
        return nextId.get();
    }

    private long id;
    private int age;
    private String name;

    public int number;

    public People(int age, String name)
    {
        this.age = age;
        this.name = name;
        this.id = nextId.incrementAndGet();
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int increment()
    {
        return ++this.age;
    }

    @Override
    public String toString()
    {
        return "People{" + "id=" + id + ", age=" + age + ", name='" + name + '\'' + '}';
    }
}
