package com.matcha.hash.app;

/**
 * Created by Administrator on 2017/4/21.
 */
public class TestHashObject2 implements Cloneable
{
    private int age;
    private String name;

    public TestHashObject2(int age, String name)
    {
        this.age = age;
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        return Integer.hashCode(age) + 31 * name.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this == obj;
    }

    @Override
    public TestHashObject2 clone()
    {
        return new TestHashObject2(age, name);
    }

    @Override
    public String toString()
    {
        return "TestHashObject2{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
