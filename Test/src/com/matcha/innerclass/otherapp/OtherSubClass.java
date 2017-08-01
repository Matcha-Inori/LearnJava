package com.matcha.innerclass.otherapp;

import com.matcha.innerclass.app.SuperClass;

/**
 * Created by Administrator on 2017/7/13.
 */
public class OtherSubClass extends SuperClass
{
    public OtherSubClass(String name)
    {
        super(name);
    }

    public void otherTest()
    {
//        this.new SuperInnerClass();
        System.out.println(this.name);
    }
}
