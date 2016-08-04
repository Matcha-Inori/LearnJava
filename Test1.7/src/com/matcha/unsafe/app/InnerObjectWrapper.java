package com.matcha.unsafe.app;

/**
 * Created by rd_xidong_ren on 2016/7/30.
 */
public class InnerObjectWrapper
{
    private InnerObject innerObject;

    public InnerObjectWrapper(InnerObject innerObject)
    {
        this.innerObject = innerObject;
    }

    public InnerObject getInnerObject()
    {
        return innerObject;
    }

    public void setInnerObject(InnerObject innerObject)
    {
        this.innerObject = innerObject;
    }

    @Override
    protected void finalize() throws Throwable
    {
        System.out.println("InnerObjectWrapper " + this.toString() + " finalize!!!");
        super.finalize();
    }

    @Override
    public String toString()
    {
        return "InnerObjectWrapper{" +
                "innerObject=" + innerObject +
                '}';
    }
}
