package com.matcha.testfinallize;

/**
 * Created by Administrator on 2016/11/23.
 */
public class OtherObject2 extends OtherObject
{
    private IPutable iPutable = null;

    public OtherObject2(String name, IPutable iPutable)
    {
        super(name);
        this.iPutable = iPutable;
    }

    @Override
    protected void finalize() throws Throwable
    {
        System.out.println("OtherObject [ " + name + " ] is dead!");
        iPutable.put(this);
    }
}
