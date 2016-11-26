package com.matcha.testfinallize;

/**
 * Created by Administrator on 2016/11/23.
 */
public class OtherObject
{
    protected String name;

    public OtherObject(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "OtherObject{" + "name='" + name + '\'' + '}';
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        System.out.println("OtherObject [ " + name + " ] is dead!");
//        while(true)
//            Thread.sleep(1000);
    }
}
