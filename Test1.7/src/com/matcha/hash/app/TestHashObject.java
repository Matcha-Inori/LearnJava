package com.matcha.hash.app;

/**
 * Created by rd_xidong_ren on 2016/7/27.
 */
public class TestHashObject
{
    private int age;
    private String name;

    public TestHashObject(int age, String name)
    {
        this.age = age;
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        Integer ageObj = new Integer(age);
        return name.hashCode() + 31 * ageObj.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!TestHashObject.class.isInstance(obj))
            return false;

        TestHashObject otherTestHashObj = (TestHashObject) obj;
        if(otherTestHashObj.age != this.age)
            return false;

        if(!equalsName(otherTestHashObj.name))
            return false;

        return true;
    }

    private boolean equalsName(String otherName)
    {
        return name == null ? otherName == null : name.equals(otherName);
    }

    @Override
    public String toString()
    {
        return "TestHashObject{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
