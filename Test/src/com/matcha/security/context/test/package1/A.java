package com.matcha.security.context.test.package1;

import com.matcha.security.context.test.package2.B;

/**
 * Created by Matcha on 2017/1/3.
 */
public class A
{
    public void methodA()
    {
        B b = new B();
        b.methodB();
    }
}
