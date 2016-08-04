package com.matcha.unsafe.app;

/**
 * Created by rd_xidong_ren on 2016/7/29.
 */
public class TestUnsafeObject
{
    public InnerObject[] innerObjects;

    public TestUnsafeObject()
    {
        innerObjects = new InnerObject[]{
            new InnerObject("AA", 10),
            new InnerObject("BB", 20),
            new InnerObject("CC", 30)
        };
    }
}
