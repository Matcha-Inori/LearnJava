package com.matcha.unsafe.app;

/**
 * Created by rd_xidong_ren on 2016/7/29.
 */
public class InnerObject
{
    public String name;
    public int age;

    public InnerObject(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "InnerObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    protected void finalize() throws Throwable
    {
        System.out.println("InnerObject " + this.toString() + " finalize!!!");
        super.finalize();
    }
}
