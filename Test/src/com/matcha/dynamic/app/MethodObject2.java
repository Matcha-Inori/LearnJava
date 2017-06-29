package com.matcha.dynamic.app;

/**
 * Created by Administrator on 2017/4/25.
 */
public class MethodObject2
{
    private String str;

    public MethodObject2(String str)
    {
        this.str = str;
    }

    public String getStr()
    {
        return str;
    }

    private void setStr(String str)
    {
        this.str = str;
    }

    public void print(String str)
    {
        System.out.println(str);
    }
}
