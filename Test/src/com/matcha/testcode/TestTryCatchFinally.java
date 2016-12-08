package com.matcha.testcode;

/**
 * Created by Matcha on 2016/11/29.
 */
public class TestTryCatchFinally
{
    private String name;
    private int age;

    public TestTryCatchFinally(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public int test1()
    {
        int x;
        try
        {
            x = 1;
            return x;
        }
        catch(Exception e)
        {
            x = 2;
            return x;
        }
        finally
        {
            x = 3;
        }
    }

    public int test2()
    {
        int x;
        try
        {
            x = 1;
            if(x == 1)
                throw new RuntimeException("test2");
            return x;
        }
        catch(Exception e)
        {
            x = 2;
            return x;
        }
        finally
        {
            x = 3;
        }
    }

    @Override
    public String toString()
    {
        return "TestTryCatchFinally{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
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
}
