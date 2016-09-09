package com.matcha.testinterface.app;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Imp implements InterfaceC
{
    private String info;

    @Override
    public void methodA(String info)
    {
        this.info = info;
    }

    @Override
    public String methodB()
    {
        return this.info;
    }
}
