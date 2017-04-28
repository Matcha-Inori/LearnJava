package com.matcha.bean.app;

/**
 * Created by Administrator on 2017/4/28.
 */
public class PersonInfo
{
    private String name;
    private int age;
    private String[] alias;

    public PersonInfo()
    {
    }

    public PersonInfo(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String[] getAlias()
    {
        return alias;
    }

    public void setAlias(String[] alias)
    {
        this.alias = alias;
    }

    public String getAlias(int index)
    {
        if(alias == null) return null;
        return index < alias.length ? alias[index] : null;
    }

    public void setAlias(int index, String alia)
    {
        if(alias == null) return;

    }
}
