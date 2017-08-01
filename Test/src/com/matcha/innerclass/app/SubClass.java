package com.matcha.innerclass.app;

/**
 * Created by Administrator on 2017/7/13.
 */
public class SubClass extends SuperClass
{
    public SubClass(String name)
    {
        super(name);
    }

    public void test()
    {
        SuperInnerClass superInnerClass = this.new SuperInnerClass();
    }
}
